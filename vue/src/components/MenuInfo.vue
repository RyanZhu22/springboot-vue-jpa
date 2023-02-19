<template>
    <!-- Btn Section -->
    <div style="padding: 10px 0">
        <el-input style="width: 200px" placeholder="Please input name" :suffix-icon="Search" v-model="menu_name" />
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
                <el-input v-model="addForm.menu_name" />
            </el-form-item>
            <el-form-item label="Path">
                <el-input v-model="addForm.menu_path" />
            </el-form-item>
            <el-form-item label="icon">
                <el-input v-model="addForm.icon" />
            </el-form-item>
            <el-form-item label="Description">
                <el-input v-model="addForm.description" />
            </el-form-item>
        </el-form>

        <template #footer>
            <span class="dialog-footer">
                <el-button @click="addDialogForm = false">Cancel</el-button>
                <el-button type="primary" @click="addMenu">
                    Confirm
                </el-button>
            </span>
        </template>
    </el-dialog>

    <el-table :data="tableData" style="width: 100%" row-key="id">
        <el-table-column type="selection" width="55" />
        <el-table-column type="index" prop="id" label="ID" width="50" />
        <el-table-column prop="menu_name" label="Name" width="180" />
        <el-table-column prop="icon" label="icon" width="180" />
        <el-table-column prop="description" label="Description" width="180" />

        <el-table-column label="Option" width="300">
            <template #default="scope">
                <el-button type="primary" @click="doAdd(scope.row.id)" v-if="!scope.row.pid">Add Submenu</el-button>
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

    <!-- Edit Pop Form -->
    <el-dialog v-model="editDialogForm" title="Form" width="50%" center>
        <el-form  label-width="100px" style="max-width: 460px">
            <el-form-item label="Name">
                <el-input v-model="editForm.menu_name" />
            </el-form-item>
            <el-form-item label="Path">
                <el-input v-model="editForm.menu_path" />
            </el-form-item>
            <el-form-item label="Icon">
                <el-input v-model="editForm.icon" />
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

    <!-- Pagination -->
    <!-- <div class="demo-pagination-block">
        <el-pagination v-model:current-page="pageNum" v-model:page-size="pageSize" :page-sizes="[5, 10, 20]"
            layout="total, sizes, prev, pager, next, jumper" :total="total" @size-change="handleSizeChange"
            @current-change="handleCurrentChange" />
    </div> -->
</template>

<script setup>
import { ref, reactive, onMounted, inject } from "vue";
import { ElMessage } from 'element-plus'
import { Search, CirclePlus, Remove } from '@element-plus/icons-vue'
import { add } from "lodash";


const $axios = inject('$axios')

const tableData = ref(null)
const menu_name = ref('')
// DO NOT Pop up Form 
const editDialogForm = ref(false)
const addDialogForm = ref(false)
const addForm = reactive({
    pid: '',
    menu_name: '',
    menu_path: '',
    icon: '',
    description: '',
})

const editForm = reactive({
    id: '',
    menu_name: '',
    menu_path: '',
    icon: '',
    description: '',
})

onMounted(() => {
    load();
})

const load = () => {
    $axios.get('/api/menu').then(res => {
        console.log(res);
        if (res.code === '200') {
            tableData.value = res.result
        }
    }).catch(err => console.log("ERROR" + err))
}


const handleSizeChange = val => {
    console.log(`${val} items per page`)
    $axios.get('/api/menu/page', { params: { pageNum: pageNum.value, pageSize: val } }).then(res => {
        console.log(res);
        tableData.value = res.result.data
    })
    pageSize.value = val
}

const handleCurrentChange = val => {
    console.log(`current page: ${val}`)
    $axios.get('/api/menu/page', { params: { pageNum: val, pageSize: pageSize.value } }).then(res => {
        console.log(res);
        tableData.value = res.result.data
    })
}

const doEdit = (row) => {
    console.log(row);
    editDialogForm.value = true
    editForm.id = row.id
    editForm.menu_name = row.menu_name
    editForm.menu_path = row.menu_path
    editForm.icon = row.icon
    editForm.description = row.description
}

const updateForm = () => {
    $axios.post('/api/menu', editForm).then(res => {
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

const addMenu = () => {
    console.log(addForm);
    $axios.post('/api/menu', addForm).then(res => {
        if (res.code === '200') {
            ElMessage({
                message: 'Add Successful',
                type: 'success'
            })
            addForm = null
            addDialogForm.value = false
            load()
        }
    })
}

const addSubMenu = (id) => {
    addDialogForm.value = true
}

const confirmDelete = (id) => {
    console.log(id);
    $axios.delete('/api/menu/' + id).then(res => {
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
    menu_name.value = ''
    load()
}
</script>

<style></style>