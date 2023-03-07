<template>
  <!-- Btn Section -->
  <div style="padding: 10px 0">
    <el-input style="width: 200px" placeholder="Please input name" :suffix-icon="Search" v-model="permission_name" />
    <el-button class="ml-5" type="primary" @click="load">Search</el-button>
    <el-button type="warning" @click="reset">Reset</el-button>
  </div>

  <div>
    <el-button type="primary" @click="doAdd()">
      Add
      <el-icon>
        <CirclePlus />
      </el-icon>
    </el-button>
    <el-button type="danger" @click="deleteBatch">
      DeleteBatch
      <el-icon>
        <Remove />
      </el-icon>
    </el-button>
  </div>

  <!-- Add Pop Form-->
  <el-dialog v-model="addDialogForm" title="Form" width="30%" center>
    <el-form label-width="100px" :model="addForm" style="max-width: 460px">
      <el-form-item label="Name">
        <el-input v-model="addForm.name" />
      </el-form-item>
      <el-form-item label="Access Path" v-if="addForm.type === 1 || addForm.type === 2">
        <el-input v-model="addForm.path" />
      </el-form-item>
      <el-form-item label="Page Path" v-if="addForm.type === 1 || addForm.type === 2">
        <el-input v-model="addForm.page" />
      </el-form-item>
      <el-form-item label="Orders" v-if="addForm.type === 1 || addForm.type === 2">
        <el-input-number v-model="addForm.orders" :min="1" />
      </el-form-item>
      <el-form-item label="icon" v-if="addForm.type === 1 || addForm.type === 2">
        <el-select v-model="addForm.icon" placeholder="Please select">
          <el-option v-for="item in icons" :key="item" :label="item" :value="item" />
        </el-select>
      </el-form-item>
      <el-form-item label="Permission" v-if="addForm.type === 2 || addForm.type === 3">
        <el-input v-model="addForm.permission" />
      </el-form-item>
      <el-form-item label="Parent ID">
        <el-select v-model="addForm.pid" placeholder="Please select">
          <el-option v-for="item in options" :key="item" :label="item" :value="item">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="Type">
        <el-radio-group v-model="addForm.type" class="ml-4">
          <el-radio :label="1" size="large">Permission Catalog</el-radio>
          <el-radio :label="2" size="large">Permission Page</el-radio>
          <el-radio :label="3" size="large">Page Button</el-radio>
        </el-radio-group>
      </el-form-item>
    </el-form>

    <template #footer>
      <span class="dialog-footer">
        <el-button @click="addDialogForm = false">Cancel</el-button>
        <el-button type="primary" @click="addPermission">
          Confirm
        </el-button>
      </span>
    </template>
  </el-dialog>

  <!-- TODO 展开箭头在id那 -->
  <el-table :data="tableData" style="width: 100%" row-key="id"  default-expand-all>
    <el-table-column type="selection" width="55" />
    <el-table-column type="index" prop="id" label="ID" width="50" />
    <el-table-column prop="name" label="Name" width="180" />
    <el-table-column prop="path" label="Access Path" width="180" />
    <el-table-column prop="page" label="Page Path" width="180" />
    <el-table-column prop="orders" label="Orders" width="180" />
    <el-table-column prop="icon" label="icon" width="180" />
    <el-table-column prop="permission" label="Permission" width="180" />
    <el-table-column prop="pid" label="Parent ID" width="180" />
    <el-table-column prop="type" label="Type" width="180" >
      <template #default="scope">
        <el-tag type="warning" v-if="scope.row.type === 1">Menu Catelog</el-tag>
        <el-tag type="" v-if="scope.row.type === 2">Menu Page</el-tag>
        <el-tag type="success" v-if="scope.row.type === 3">Page Button</el-tag>
      </template>
    </el-table-column>
    <el-table-column label="Option" width="300">
      <template #default="scope">
        <el-button type="primary" @click="doAdd(scope.row.id)" v-if="!scope.row.pid">Add Subpermission</el-button>
        <el-button type="success" @click="doEdit(scope.row)">Edit</el-button>
        <el-popconfirm confirm-button-text="Yes" cancel-button-text="No" title="Are you sure to delete this?"
          @confirm="confirmDelete(scope.row.id)" @cancel="cancelEvent">
          <template #reference>
            <el-button type="danger">Delete</el-button>
          </template>
        </el-popconfirm>
      </template>
    </el-table-column>
  </el-table>

  <!-- Edit Pop Form -->
  <el-dialog v-model="editDialogForm" title="permission Information" width="50%" center>
    <el-form label-width="100px" style="max-width: 460px">
      <el-form-item label="Name">
        <el-input v-model="editForm.permission_name" />
      </el-form-item>
      <el-form-item label="Path">
        <el-input v-model="editForm.permission_path" />
      </el-form-item>
      <el-form-item label="Icon">
        <el-select clearable v-model="editForm.icon" placeholder="Please select">
          <el-option v-for="item in options" :key="item.value" :label="item.name" :value="item.value">
            <component :is="item.value" style="width: 12px;" /> {{ item.name }}
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="Description">
        <el-input v-model="editForm.description" />
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="editDialogForm = false">Cancel</el-button>
        <el-button type="primary" @click="updateForm">
          Confirm
        </el-button>
      </span>
    </template>
  </el-dialog>

</template>

<script setup>
import { ref, reactive, onMounted, inject } from "vue";
import { ElMessage } from 'element-plus'
import { Search, CirclePlus, Remove, User } from '@element-plus/icons-vue'

const $axios = inject('$axios')

const tableData = ref(null)
const permission_name = ref('')
const options = ref([])
// DO NOT Pop up Form 
const editDialogForm = ref(false)
const addDialogForm = ref(false)
const addForm = reactive({
  name: '',
  path: '',
  page: '',
  orders: 0,
  icon: '',
  permission: '',
  pid: null,
  type: null,
})

const editForm = reactive({
  id: '',
  permission_name: '',
  permission_path: '',
  icon: '',
  description: '',
})

// icons dict
const icons = ref([

])

onMounted(() => {
  load();
})

const load = () => {
  $axios.get('/api/permission/tree', { params: { name: permission_name.value } }).then(res => {
    console.log(res);
    if (res.code === '200') {
      tableData.value = res.result
    }
  }).catch(err => console.log("ERROR" + err))
}

const doEdit = (row) => {
  console.log(row);
  editDialogForm.value = true
  editForm.id = row.id
  editForm.permission_name = row.permission_name
  editForm.permission_path = row.permission_path
  editForm.icon = row.icon
  editForm.description = row.description

  $axios.get('/api/permission/icons').then(res => {
    console.log(res);
    if (res.code === '200') {
      options.value = res.result
    }
  }).catch(err => console.log("ERROR" + err))
}

const updateForm = () => {
  $axios.post('/api/permission', editForm).then(res => {
    console.log(res);
    if (res.code === '200') {
      ElMessage({
        message: 'Edit Successful',
        type: 'success'
      })
      editDialogForm.value = false
      load()
    }
  })
}

const doAdd = (id) => {
  console.log(id);
  console.log(addForm.value);
  addDialogForm.value = true
  addForm.pid = id
  console.log(addForm);
}

const deleteBatch = () => {

}

const addPermission = () => {
  console.log(addForm);
  $axios.post('/api/permission', addForm).then(res => {
    if (res.code === '200') {
      ElMessage({
        message: 'Add Successful',
        type: 'success'
      })
      addDialogForm.value = false
      load()
    }
  })
}

const addSubpermission = (id) => {
  addDialogForm.value = true
}

const confirmDelete = (id) => {
  console.log(id);
  $axios.post('/api/permission/' + id).then(res => {
    console.log(res)
    load();
    ElMessage({
      message: 'Delete Successful',
      type: 'success'
    })
  }).catch(err => console.log("ERROR" + err))
}

const cancelEvent = () => {
  console.log('cancel!')
}

const reset = () => {
  permission_name.value = ''
  load()
}
</script>

<style></style>