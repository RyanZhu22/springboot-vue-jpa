<template>
    <div class="wrapper">
      <div class="container">
        <div class="header">LOGIN</div>
        <el-form
          status-icon
          label-width="120px"
          class="demo-ruleForm">
          <el-form-item label="username" prop="pass">
            <el-input type="username" autocomplete="off" />
          </el-form-item>
          <el-form-item label="password" prop="pass">
            <el-input type="password" autocomplete="off" />
          </el-form-item>
          <el-form-item>
            <el-button type="primary">Login</el-button>
            <el-button type="warning">Reset</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
</template>
  
<script setup>
  
</script>
  
<style>
.wrapper {
  height: 100vh;
  background-image: linear-gradient(to bottom right, #FC466B, #3F5EFB);
  overflow: hidden;
}

.container {
  width: 350px; 
  height: 280px; 
  background-color: #fff; 
  margin: 200px auto; 

  padding: 20px; 
  border-radius: 10px
}
</style>