<template>
  <div>
    <div>
      <el-input v-model="code" placeholder="Please input the code" class="w300"/>
      <el-button type="primary" class="ml5" @click="load">
        <el-icon style="vertical-align: middle">
          <Search/>
        </el-icon>
        <span style="vertical-align: middle"> search </span>
      </el-button>
      <el-button type="warning" class="ml5" @click="reset">
        <el-icon style="vertical-align: middle">
          <RefreshLeft/>
        </el-icon>
        <span style="vertical-align: middle"> reset </span>
      </el-button>
    </div>

    <div style="margin: 10px 0">
      <el-button type="success" @click="handleAdd">
        <el-icon style="vertical-align: middle">
          <Plus/>
        </el-icon>
        <span style="vertical-align: middle"> add </span>
      </el-button>
      <!--      <el-upload-->
      <!--        class="ml5"-->
      <!--        :show-file-list="false"-->
      <!--        style="display: inline-block; position: relative; top: 3px"-->
      <!--        :action='`http://${config.serverUrl}/dict/import`'-->
      <!--        :on-success="handleImportSuccess"-->
      <!--        :headers="{ Authorization: token}"-->
      <!--      >-->
      <!--        <el-button type="primary">-->
      <!--          <el-icon style="vertical-align: middle">-->
      <!--            <Bottom/>-->
      <!--          </el-icon>-->
      <!--          <span style="vertical-align: middle"> Import </span>-->
      <!--        </el-button>-->
      <!--      </el-upload>-->
      <el-button type="primary" @click="exportData" class="ml5">
        <el-icon style="vertical-align: middle">
          <Top/>
        </el-icon>
        <span style="vertical-align: middle"> export </span>
      </el-button>
      <el-popconfirm title="Are you sure to delete?" @confirm="confirmDelBatch">
        <template #reference>
          <el-button type="danger" style="margin-left: 5px">
            <el-icon style="vertical-align: middle">
              <Remove/>
            </el-icon>
            <span style="vertical-align: middle"> delete batch </span>
          </el-button>
        </template>
      </el-popconfirm>
    </div>

    <div style="margin: 10px 0">
      <el-table :data="state.tableData" stripe border @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55"/>
        <el-table-column prop="id" label="id"></el-table-column>
        <el-table-column prop="code" label="code"></el-table-column>
        <el-table-column prop="value" label="value">
          <template #default="scope">
            <el-icon>
              <component :is="scope.row.value"/>
            </el-icon>
          </template>
        </el-table-column>
        <el-table-column prop="type" label="type"></el-table-column>

        <el-table-column label="option" width="180">
          <template #default="scope">
            <el-button type="primary" @click="handleEdit(scope.row)">edit</el-button>
            <el-popconfirm title="Are you sure to delete?" @confirm="del(scope.row.id)">
              <template #reference>
                <el-button type="danger">delete</el-button>
              </template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <div style="margin: 10px 0">
      <el-pagination
        @current-change="load"
        @size-change="load"
        v-model:current-page="pageNum"
        v-model:page-size="pageSize"
        background
        :page-sizes="[2, 5, 10, 20]"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
      />
    </div>

    <el-dialog v-model="dialogFormVisible" title="Add Form" width="40%">
      <el-form
        ref="ruleFormRef"
        :rules="rules"
        :model="state.form"
        label-width="80px"
        style="padding: 0 20px"
        status-icon>
        <el-form-item prop="code" label="code">
          <el-input v-model="state.form.code" autocomplete="off"/>
        </el-form-item>
        <el-form-item prop="value" label="value">
          <el-input v-model="state.form.value" autocomplete="off"/>
        </el-form-item>
        <el-form-item prop="type" label="type">
          <el-select v-model="state.form.type" style="width: 100%">
            <el-option v-for="item in ['icon']" :key="item" :label="item" :value="item"></el-option>
          </el-select>
        </el-form-item>

      </el-form>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogFormVisible = false">cancel</el-button>
        <el-button type="primary" @click="saveOrUpdate">
          save
        </el-button>
      </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { inject, nextTick, onMounted, reactive, ref } from "vue";
import { ElMessage } from "element-plus";
import { useUserStore } from "../store/user.js";

const $axios = inject('$axios')
const userStore = useUserStore();
const token = userStore.getBearerToken

const code = ref('')
const pageNum = ref(1)
const pageSize = ref(5)
const total = ref(0)

const state = reactive({
  tableData: [],
  form: {}
})
const multipleSelection = ref([])

onMounted(() => {
  load();
})

// 批量删除
const handleSelectionChange = (val) => {
  multipleSelection.value = val
}

const confirmDelBatch = () => {
  if (!multipleSelection.value || !multipleSelection.value.length) {
    ElMessage.warning("请选择数据")
    return
  }
  const idArr = multipleSelection.value.map(v => v.id)
  $axios.post('/dict/del/batch', idArr).then(res => {
    if (res.code === '200') {
      ElMessage.success('操作成功')
      load()  // 刷新表格数据
    } else {
      ElMessage.error(res.msg)
    }
  })
}

const load = () => {
  $axios.get('/api/dict/page', {
    params: {
      code: code.value,
      page: pageNum.value,
      size: pageSize.value
    }
  }).then(res => {
    console.log(res)
	  if (res.code === 200) {
      state.tableData = res.result.content
      total.value = res.result.totalElements
	  } else {
      ElMessage.error('Loading Failed')
	  }
  }).catch(err => {
    console.log("ERROR:" + err)
  })
}

const reset = () => {
  code.value = '';
  load();
}

const dialogFormVisible = ref(false)

const rules = reactive({
  code: [
    {required: true, message: '请输入名称', trigger: 'blur'},
  ]
})
const ruleFormRef = ref()

// add
const handleAdd = () => {
  dialogFormVisible.value = true
  nextTick(() => {
    ruleFormRef.value.resetFields()
    state.form = {}
  })
}

// saveOrUpdate
const saveOrUpdate = () => {
  ruleFormRef.value.validate(valid => {
    if (valid) {
      // determine add or update
      state.form.id ? update(state.form.id) : add()
    }
  })
}

// save
const add = () => {
  $axios.post('/api/dict', state.form).then(res => {
    console.log(res)
    if (res.code === '200') {
      ElMessage.success('Add Successful')
      dialogFormVisible.value = false;
      load();
    } else {
      ElMessage.error(res.msg)
    }
  })
}

// update
const update = (id) => {
  console.log(state.form)
  $axios.put(`/api/dict/${id}`, state.form).then(res => {
    console.log(res)
    if (res.code === '200') {
      ElMessage.success('Update Successful')
      dialogFormVisible.value = false;
      load();
    } else {
      ElMessage.error(res.msg)
    }
  })
}

// 编辑
const handleEdit = (raw) => {
  dialogFormVisible.value = true
  nextTick(() => {
    ruleFormRef.value.resetFields()
    state.form = JSON.parse(JSON.stringify(raw))
  })
}

// 删除
const del = (id) => {
  $axios.delete('/dict/' + id).then(res => {
    if (res.code === '200') {
      ElMessage.success('操作成功')
      load()  // 刷新表格数据
    } else {
      ElMessage.error(res.msg)
    }
  })
}

// 导出接口
const exportData = () => {
  window.open(`http://${config.serverUrl}/dict/export`)
}

const handleImportSuccess = () => {
  // 刷新表格
  load()
  ElMessage.success("导入成功")
}
</script>

<style>

</style>


