<template>
  <el-table :data="tableData" style="width: 100%">
    <el-table-column type="selection" width="55" />
    <el-table-column type="index" prop="id" label="ID" width="50" />
    <el-table-column prop="fileName" label="Name" width="180" />
    <el-table-column prop="fileType" label="Type" width="180" />
    <el-table-column prop="fileSize" label="Size (kb)" width="180" />
    <el-table-column label="Download" width="180">
      <template #default="scope">
        <el-button type="primary" @click="download(scope.row)">Download</el-button>
      </template>
    </el-table-column>
    <el-table-column label="Enable" width="180">
      <template #default="scope">
        <el-switch v-model="scope.row.enable" class="mt-2" style="margin-left: 24px" inline-prompt :active-icon="Check"
          :inactive-icon="Close" @change="handleSwitchChange(scope.row)" />
      </template>
    </el-table-column>

    <el-table-column label="Option" width="180">
      <template #default="scope">
        <el-button type="danger" @click="doDelete(scope.row)">Delete</el-button>
      </template>
    </el-table-column>
  </el-table>

  <!-- Pagination -->
  <div class="demo-pagination-block">
    <el-pagination v-model:current-page="pageNum" v-model:page-size="pageSize" :page-sizes="[5, 10, 20]"
      layout="total, sizes, prev, pager, next, jumper" :total="total" @size-change="handleSizeChange"
      @current-change="handleCurrentChange" />
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, inject } from "vue";
import { ElMessage } from 'element-plus'
import { Check, Close } from '@element-plus/icons-vue'


const $axios = inject('$axios')

const value = ref(true)
const tableData = ref(null)
const pageNum = ref(1)
const pageSize = ref(5)
const total = ref(25)

onMounted(() => {
  load();
})

const load = () => {
  console.log(typeof pageNum.value);
  console.log(pageSize.value);
  $axios.get('/api/file/page', { params: { pageNum: pageNum.value, pageSize: pageSize.value } }).then(res => {
    console.log(res);
    if (res.code === '200') {
      total.value = res.result.total
      tableData.value = res.result.data
    }
  }).catch(err => console.log("ERROR" + err))
}

const handleSizeChange = (val) => {
  console.log(`${val} items per page`)
}
const handleCurrentChange = (val) => {
  console.log(`current page: ${val}`)
}

const handleSwitchChange = (row) => {
  console.log(row)
  $axios.post('/api/file/update', JSON.stringify({ id: row.id, enable: row.enable })).then(res => {
    console.log(res);
    ElMessage.success('Update Successful')
  })
}


const download = (row) => {
  window.open(row.url)
}

const doDelete = (row) => {
  $axios.delete('/api/file/' + row.id).then(res => {
    console.log(res)
    load();
    ElMessage({
      message: 'Delete Successful',
      type: 'success'
    })
  }).catch(err => console.log("ERROR" + err))
}

</script>

<style></style>