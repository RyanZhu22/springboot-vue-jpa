<template>
    <!-- Btn Section -->
    <div style="padding: 10px 0">
        <el-input style="width: 200px" placeholder="Please input username" suffix-icon="el-icon-search" v-model="username" />
        <el-input style="width: 200px" placeholder="Please input email" suffix-icon="el-icon-message" class="ml-5" v-model="email" />
        <el-input style="width: 200px" placeholder="Please input address" suffix-icon="el-icon-position" v-model="address" class="ml-5" />
        <el-button class="ml-5" type="primary" @click="load">Searching</el-button>
        <el-button type="primary" @click="reset">Reset</el-button>
    </div>

    <div style="padding: 10px">
        <el-button type="primary" @click="doAdd">Add</el-button>
        <!-- confirm 点击确定按钮触发 delBatch事件 -->
        <el-upload 
            action="http://localhost:8001/api/user/import" 
            :show-file-list="false" accept=".xlsx"
            :on-success="handleSuccess"
            style="display: inline-block">
            <el-button type="primary" class="ml-5">import</el-button>
        </el-upload>
        <el-button type="primary" class="ml-5" @click="doExport">Export</el-button>
    </div>

    <!-- Add Pop Form-->
    <el-dialog v-model="addDialogForm" title="Form" width="30%" center>
        <el-form  label-width="100px" :model="addForm" 
            style="max-width: 460px">
            <el-form-item label="Username">
                <el-input v-model="addForm.username" />
            </el-form-item>
            <el-form-item label="Nickname">
                <el-input v-model="addForm.nickname" />
            </el-form-item>
            <el-form-item label="Email">
                <el-input v-model="addForm.email" />
            </el-form-item>
            <el-form-item label="Phone">
                <el-input v-model="addForm.phone" />
            </el-form-item>
            <el-form-item label="Address">
                <el-input v-model="addForm.address" />
            </el-form-item>
        </el-form>
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="addDialogForm = false">Cancel</el-button>
                <el-button type="primary" @click="addUser">
                    Confirm
                </el-button>
            </span>
        </template>
    </el-dialog>

    <!-- Data Table -->
    <el-scrollbar>
        <el-table :data="tableData">
            <el-table-column type="selection" width="55" />
            <el-table-column type="index" prop="id" label="ID" width="50" />
            <el-table-column prop="username" label="Username" width="100" />
            <el-table-column prop="nickname" label="Nickname" width="140" />
            <el-table-column prop="email" label="Email" width="140" />
            <el-table-column prop="phone" label="Phone" width="120" />
            <el-table-column prop="address" label="Address" />
            <el-table-column label="Option">
                <template #default="scope">
                    <el-button type="primary" @click="edit(scope.row)">Edit</el-button>
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

        <!-- Pagination -->
        <div class="demo-pagination-block" id="pagination">
            <el-pagination v-model:current-page="currentPage"  v-model:page-size="pageSize"
                :page-sizes="[5, 10, 20]" layout="total, sizes, prev, pager, next, jumper" :total="total"
                @size-change="handleSizeChange" @current-change="handleCurrentChange" />
        </div>

        <!-- Edit Pop Form -->
        <el-dialog v-model="editDialogForm" title="Form" width="50%" center>
            <el-form  label-width="100px" 
                style="max-width: 460px">
                <el-form-item label="Username">
                    <el-input v-model="editForm.username" />
                </el-form-item>
                <el-form-item label="Nickname">
                    <el-input v-model="editForm.nickname" />
                </el-form-item>
                <el-form-item label="Email">
                    <el-input v-model="editForm.email" />
                </el-form-item>
                <el-form-item label="Phone">
                    <el-input v-model="editForm.phone" />
                </el-form-item>
                <el-form-item label="Address">
                    <el-input v-model="editForm.address" />
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
    </el-scrollbar>
</template>

<script setup>
import { ref, reactive, onMounted, inject } from "vue";
import router from '../router'
import { ElMessage } from 'element-plus'

const $axios = inject('$axios')
const username = ref('')
const email = ref('')
const address = ref('')
const currentPage = ref(1)
const pageSize = ref(5)
const tableData = ref(null)
const allTableData = ref(null)
const total = ref(25)
// DO NOT Pop up Form 
const editDialogForm = ref(false)
const addDialogForm = ref(false)

const editForm = reactive({
    id: '',
    username: '',
    nickname: '',
    email: '',
    phone: '',
    address: '',
})

const addForm = reactive({
    username: '',
    nickname: '',
    email: '',
    phone: '',
    address: '',
})

const handleSizeChange = val => {
    console.log(`${val} items per page`)
    $axios.get('/api/user/page', {params: {pageNum: currentPage.value, pageSize: val, is_delete: 0}}).then(res => {
        tableData.value = res.data
    })
    pageSize.value = val
}

const handleCurrentChange = val => {
    console.log(`current page: ${val}`)
    $axios.get('/api/user/page', {params: {pageNum: val, pageSize: pageSize.value, is_delete: 0}}).then(res => {
        tableData.value = res.data
    })
}

onMounted(() => {
    load();
    // findAll();
})

const load = () => {
    $axios.get('/api/user/page', {params: {pageNum: currentPage.value, pageSize: pageSize.value, username: username.value, email: email.value, address: address.value, is_delete: 0}}).then(res => {
        console.log(res);
        tableData.value = res.data
        total.value = res.total
    }).catch(err => console.log(err))
}

const findAll = () => {
    $axios.get('/api/user').then(res => {
        console.log(res);
        allTableData.value = res
    })
}

const reset = () => {
    username.value = ''
    email.value = ''
    address.value = ''
    load();
}

const doAdd = () => {
    addDialogForm.value = true
}

const addUser = () => {
    console.log(addForm);
    $axios.post('/api/user', addForm).then(res => {
        console.log(res);
        ElMessage({
            message: 'Add Successfully',
            type: 'success',
        })
    }).catch(err => console.log(err))
}

const handleSuccess = () => {
    load();
    ElMessage({
        message: 'Import Successfully',
        type: 'success',
    })
}

const doExport = () => {
    window.open('http://localhost:8001/api/user/export')
}

const edit = (row) => {
    console.log(row);
    editDialogForm.value = true
    editForm.id = row.id
    editForm.username = row.username
    editForm.email = row.email
    editForm.phone = row.phone
    editForm.nickname = row.nickname
    editForm.address = row.address
}

const validate = () => {
    editForm.forEach(element => {
        if (element === '') {
            return false
        }
    });
}

const updateForm = () => {
    // Fail to validate
    if (!validate) {
        return false
    }

    console.log(editForm);
    // pass to validate
    editDialogForm.value = false
    $axios.post('/api/user', editForm).then(res => {
        console.log(res);
        load();
        ElMessage({
            message: 'Edit Successfully',
            type: 'success',
        })
    })
}

const confirmDelete = (id) => {
  console.log('confirm!')
  console.log(id);
  $axios.post('/api/user/' + id).then(res => {
        ElMessage({
            message: 'Delete Successfully',
            type: 'success',
        })
        load();
    })
}

const cancelEvent = () => {
  console.log('cancel!')
}


</script>

<style>
.el-main {
    position: relative;
}

#pagination {
    margin-top: 5px;
}

.ml-5 {
    margin-left: 5px;
}
</style>
