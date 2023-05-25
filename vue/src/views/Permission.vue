<template>
	<!-- Btn Section -->
    <div>
        <el-button type="primary" @click="doAdd">
            Add
            <el-icon>
                <CirclePlus/>
            </el-icon>
        </el-button>
        <el-button type="danger" @click="deleteBatch">
            DeleteBatch
            <el-icon>
                <Remove/>
            </el-icon>
        </el-button>
    </div>

	<!-- Add Pop Form-->
    <el-dialog v-model="addDialogForm" title="Form" width="30%" center>
        <el-form label-width="100px" :model="addForm" style="max-width: 460px">
            <el-form-item label="Name">
                <el-input v-model="addForm.name"/>
            </el-form-item>
            <el-form-item
                    label="Access Path"
                    v-if="addForm.type === 1 || addForm.type === 2"
            >
                <el-input v-model="addForm.path"/>
            </el-form-item>
            <el-form-item
                    label="Page Path"
                    v-if="addForm.type === 1 || addForm.type === 2"
            >
                <el-input v-model="addForm.page"/>
            </el-form-item>
            <el-form-item
                    label="Orders"
                    v-if="addForm.type === 1 || addForm.type === 2"
            >
                <el-input-number v-model="addForm.orders" :min="1"/>
            </el-form-item>
            <el-form-item
                    label="icon"
                    v-if="addForm.type === 1 || addForm.type === 2"
            >
                <el-select v-model="addForm.icon" placeholder="Please select">
                    <el-option
                            v-for="item in icons"
                            :key="item"
                            :label="item"
                            :value="item"
                    />
                </el-select>
            </el-form-item>
            <el-form-item
                    label="Auth"
                    v-if="addForm.type === 2 || addForm.type === 3"
            >
                <el-input v-model="addForm.auth"/>
            </el-form-item>
            <el-form-item label="Parent ID">
                <el-tree-select
                        v-model="addForm.pid"
                        :data="state.tableData"
                        :props="{ label: 'name', value: 'id' }"
                        :render-after-expand="false"
                        check-strictly
                />
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
        <el-button type="primary" @click="addPermission"> Confirm </el-button>
      </span>
        </template>
    </el-dialog>

	<!-- TODO 展开箭头在id那 -->
    <el-table
            :data="state.tableData"
            style="width: 100%"
            row-key="id"
            default-expand-all
    >
        <el-table-column type="selection" width="55"/>
        <el-table-column prop="id" label="ID" width="50"/>
        <el-table-column prop="name" label="Name" width="180"/>
        <el-table-column prop="path" label="Access Path" width="180"/>
        <el-table-column prop="page" label="Page Path" width="180"/>
        <el-table-column prop="orders" label="Orders" width="180"/>
        <el-table-column prop="icon" label="icon" width="100"/>
        <el-table-column prop="auth" label="Auth" width="100"/>
        <el-table-column prop="pid" label="Parent ID" width="100"/>
        <el-table-column prop="type" label="Type" width="180">
            <template #default="scope">
                <el-tag type="warning" v-if="scope.row.type === 1"
                >Permission Catelog
                </el-tag
                >
                <el-tag type="" v-if="scope.row.type === 2">Permission Page</el-tag>
                <el-tag type="success" v-if="scope.row.type === 3">Page Button</el-tag>
            </template>
        </el-table-column>

        <el-table-column prop="hide" label="Hide" width="180">
            <template #default="scope">
                <el-switch
                        v-model="scope.row.hide"
                        @change="changeHide(scope.row)"
                ></el-switch>
            </template>
        </el-table-column>

        <el-table-column label="Option" width="300">
            <template #default="scope">
                <el-button
                        type="primary"
                        @click="doAdd(scope.row.id)"
                        v-if="!scope.row.pid"
                >Add Subpermission
                </el-button
                >
                <el-button type="success" @click="handleEdit(scope.row)">Edit</el-button>
                <el-popconfirm
                        confirm-button-text="Yes"
                        cancel-button-text="No"
                        title="Are you sure to delete this?"
                        @confirm="confirmDelete(scope.row.id)"
                        @cancel="cancelEvent"
                >
                    <template #reference>
                        <el-button type="danger">Delete</el-button>
                    </template>
                </el-popconfirm>
            </template>
        </el-table-column>
    </el-table>

	<!-- Edit Pop Form -->
    <el-dialog
            v-model="editDialogFormVisible"
            title="permission Information"
            width="50%"
            center
    >
        <el-form label-width="100px" :model="state.form" style="max-width: 460px">
            <el-form-item label="Name">
                <el-input v-model="state.form.name"/>
            </el-form-item>
            <el-form-item
                    label="Access Path"
                    v-if="state.form.type === 1 || state.form.type === 2"
            >
                <el-input v-model="state.form.path"/>
            </el-form-item>
            <el-form-item
                    label="Page Path"
                    v-if="state.form.type === 1 || state.form.type === 2"
            >
                <el-input v-model="state.form.page"/>
            </el-form-item>
            <el-form-item
                    label="Orders"
                    v-if="state.form.type === 1 || state.form.type === 2"
            >
                <el-input-number v-model="state.form.orders" :min="1"/>
            </el-form-item>
            <el-form-item
                    label="icon"
                    v-if="state.form.type === 1 || state.form.type === 2"
            >
                <el-select v-model="state.form.icon" placeholder="Please select">
                    <el-option
                            v-for="icon in icons"
                            :key="icon.id"
                            :label="icon.code"
                            :value="icon.value"
                    >
                        <el-icon>
                            <component :is="icon.value"/>
                        </el-icon>
                        <span style="font-size: 14px;margin-left: 2px;margin-bottom: 2px">{{ icon.code }}</span>
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item
                    label="Auth"
                    v-if="state.form.type === 2 || state.form.type === 3"
            >
                <el-input v-model="state.form.auth"/>
            </el-form-item>
            <el-form-item label="Parent ID">
                <el-tree-select
                        v-model="state.form.pid"
                        :data="state.tableData"
                        @node-click="handleNodeClick"
                        :props="{ label: 'name', value: 'id' }"
                        :render-after-expand="false"
                        check-strictly
                />
            </el-form-item>
            <el-form-item label="Type">
                <el-radio-group v-model="state.form.type" class="ml-4">
                    <el-radio :label="1" size="large">Permission Catalog</el-radio>
                    <el-radio :label="2" size="large">Permission Page</el-radio>
                    <el-radio :label="3" size="large">Page Button</el-radio>
                </el-radio-group>
            </el-form-item>
        </el-form>

        <template #footer>
      <span class="dialog-footer">
        <el-button @click="editDialogFormVisible = false">Cancel</el-button>
        <el-button type="primary" @click="updateForm"> Confirm </el-button>
      </span>
        </template>
    </el-dialog>
</template>

<script setup>
import {inject, nextTick, onMounted, reactive, ref} from "vue";
import {ElMessage} from "element-plus";
import * as editForm from "element-plus/es/locale/index";

const $axios = inject("$axios");

const state = reactive({
    tableData: [],
    form: {}
})

// Icon dict list
const icons = ref([])
const editDialogFormVisible = ref(false);
const addDialogForm = ref(false);
const addForm = reactive({
    name: "",
    path: "",
    page: "",
    orders: 0,
    icon: "",
    auth: "",
    pid: null,
    type: null,
});

onMounted(() => {
    load();
});

const load = () => {
    $axios
        .get("/api/permission/tree")
        .then((res) => {
            console.log(res);
            if (res.code === "200") {
                state.tableData = res.result;
            }
        })
        .catch((err) => console.log("ERROR" + err));
};

const handleEdit = (row) => {
    console.log(row);
    editDialogFormVisible.value = true;
    state.form = row

    $axios
        .get("/api/permission/icons")
        .then((res) => {
            console.log(res);
            if (res.code === "200") {
                icons.value = res.result;
            }
        })
        .catch((err) => console.log("ERROR" + err));
};

const updateForm = () => {
    console.log(editForm);
    $axios.post("/api/permission", editForm).then((res) => {
        console.log(res);
        if (res.code === "200") {
            ElMessage({
                message: "Edit Successful",
                type: "success",
            });
            editDialogFormVisible.value = false;
            load();
        }
    });
};

const changeHide = (row) => {
    $axios
        .post(
            "/api/permission/hide",
            JSON.stringify({id: row.id, hide: row.hide})
        )
        .then((res) => {
            console.log(res);
            if (res.code === "200") {
                ElMessage.success("Change Successful");
                load();
            } else {
                ElMessage.error("Change Failed");
            }
        });
};

const doAdd = (id) => {
    console.log(id);
    console.log(addForm.value);
    addDialogForm.value = true;
    addForm.pid = id;
    console.log(addForm);
};

const handleNodeClick = (data) => {
    if (data.id === editForm.id) {
        ElMessage.warning("You do not select parent node");
        nextTick(() => {
            editForm.pid = null;
            console.log(editForm);
        });
    }
};

const deleteBatch = () => {
};

const addPermission = () => {
    console.log(addForm);
    $axios.post("/api/permission", addForm).then((res) => {
        if (res.code === "200") {
            ElMessage.success("Add Successful");
            addDialogForm.value = false;
            load();
        } else {
            ElMessage.error("Add Failed");
        }
    });
};

const confirmDelete = (id) => {
    console.log(id);
    $axios
        .post("/api/permission/" + id)
        .then((res) => {
            console.log(res);
            load();
            ElMessage({
                message: "Delete Successful",
                type: "success",
            });
        })
        .catch((err) => console.log("ERROR" + err));
};

const cancelEvent = () => {
    console.log("cancel!");
};
</script>

<style></style>
