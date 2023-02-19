<template>
    <!-- Btn Section -->
    <div style="padding: 10px 0">
        <el-input style="width: 200px" placeholder="Please input name" :suffix-icon="Search" v-model="role_name" />
        <el-button class="ml-5" type="primary" @click="load">Search</el-button>
        <el-button type="warning" @click="reset">Reset</el-button>
    </div>

    <div>
        <el-button type="primary" @click="doAdd">
            Add
            <el-icon><CirclePlus /></el-icon>
        </el-button>
        <el-button type="danger" @click="deleteBatch">
            DeleteBatch
            <el-icon><Remove /></el-icon>
        </el-button>
    </div>

    <!-- Add Pop Form-->
    <el-dialog v-model="addDialogForm" title="Form" width="30%" center>
        <el-form label-width="100px" :model="addForm" style="max-width: 460px">
            <el-form-item label="Name">
                <el-input v-model="addForm.role_name" />
            </el-form-item>
            <el-form-item label="Description">
                <el-input v-model="addForm.description" />
            </el-form-item>
        </el-form>
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="addDialogForm = false">Cancel</el-button>
                <el-button type="primary" @click="addRole">
                    Confirm
                </el-button>
            </span>
        </template>
    </el-dialog>

    <!-- table -->
    <el-table :data="tableData" style="width: 100%">
        <el-table-column type="selection" width="55" />
        <el-table-column type="index" prop="id" label="ID" width="50" />
        <el-table-column prop="role_name" label="Name" width="180" />
        <el-table-column prop="description" label="Description" width="180" />

        <el-table-column label="Option" width="500">
            <template #default="scope">
                <el-button type="primary" @click="assignPermission">Assign Permissions</el-button>
                <el-button type="success" @click="doEdit(scope.row)">Edit</el-button>
                <el-popconfirm 
                        confirm-button-text="Yes"
                        cancel-button-text="No"
                        title="Are you sure to delete this?" 
                        @confirm="confirmDelete(scope.row.id)"
                        @cancel="cancelEvent">
                        <template #reference>
                            <el-button type="danger">Delete</el-button>
                        </template>
                    </el-popconfirm>
            </template>
        </el-table-column>
    </el-table>

    <!-- Assign Role -->
    <el-dialog v-model="addDialogTree" title="Form" width="30%" center>
        <el-tree :props="defaultProps" :data="menuList" show-checkbox/>
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="addDialogForm = false">Cancel</el-button>
                <el-button type="primary" @click="addRole">
                    Confirm
                </el-button>
            </span>
        </template>
    </el-dialog>

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
import { Search, CirclePlus, Remove } from '@element-plus/icons-vue'


const $axios = inject('$axios')

const tableData = ref(null)
const pageNum = ref(1)
const pageSize = ref(5)
const total = ref(25)
const role_name = ref('')
const addDialogForm = ref(false)
const addDialogTree = ref(false)
const addForm = reactive({
    role_name: '',
    description: '',
})

const menuList = ref(null)

const defaultProps = {
  children: 'children',
  label: 'menu_name',
}

onMounted(() => {
    load();
})


const load = () => {
    $axios.get('/api/role/page', { params: { pageNum: pageNum.value, pageSize: pageSize.value, role_name: role_name.value }}).then(res => {
        console.log(res);
        if (res.code === '200') {
            total.value = res.result.total
            tableData.value = res.result.data
        }
    }).catch(err => console.log("ERROR" + err))
}


const handleSizeChange = val => {
    console.log(`${val} items per page`)
    $axios.get('/api/role/page', { params: { pageNum: pageNum.value, pageSize: val } }).then(res => {
        console.log(res);
        tableData.value = res.result.data
    })
    pageSize.value = val
}

const handleCurrentChange = val => {
    console.log(`current page: ${val}`)
    $axios.get('/api/role/page', { params: { pageNum: val, pageSize: pageSize.value } }).then(res => {
        console.log(res);
        tableData.value = res.result.data
    })
}

const doAdd = () => {
    addDialogForm.value = true
}

const deleteBatch = () => {

}

const addRole = () => {
    console.log(addForm);
    $axios.post('/api/role', addForm).then(res => {
        if (res.code === '200') {
            ElMessage({
                message: 'Add Successful',
                type: 'success'
            })
            // init addForm value
            addForm.role_name = ''
            addForm.description = ''
            addDialogForm.value = false
            load()
        }

    })
}

const confirmDelete = (id) => {
    $axios.delete('/api/role/' + id).then(res => {
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
    role_name.value = ''
    load()
}

const assignPermission = () => {
    addDialogTree.value = true
    $axios.get('/api/menu').then(res => {
        console.log(res);
        if (res.code === '200') {
            menuList.value = res.result
        }
    }).catch(err => console.log("ERROR" + err))
}

</script>
  
<style>

</style>