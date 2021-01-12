<template>
    <section>
        <!--高级查询-->
        <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
            <el-form :inline="true" :model="filters">
                <el-form-item label="部门名称">
                    <el-input v-model="filters.name" placeholder="部门名称"></el-input>
                </el-form-item>
                <el-form-item label="是否启用">
                    <el-select v-model="filters.state" clearable placeholder="是否启用">
                        <el-option label="启用" value="1"></el-option>
                        <el-option label="禁用" value="0"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" v-on:click="getUsers">查询</el-button>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="handleAdd">新增</el-button>
                </el-form-item>
            </el-form>

        </el-col>
        <!--列表显示-->
        <el-table :data="departments" highlight-current-row v-loading="listLoading" @selection-change="selsChange" style="width: 100%;">
            <el-table-column type="selection" width="55">
            </el-table-column>
            <el-table-column type="index" width="60">
            </el-table-column>
            <el-table-column prop="sn" label="部门编号" >
            </el-table-column>
            <el-table-column prop="name" label="部门名称" >
            </el-table-column>
            <el-table-column prop="state" label="是否启用" >
                <template slot-scope="scope">
                    <span style="color: green" v-if="scope.row.state==1">启用</span>
                    <span style="color: red" v-if="scope.row.state==0">禁用</span>
                </template>
            </el-table-column>
            <el-table-column prop="manager.username" label="项目经理" >
            </el-table-column>
            <el-table-column prop="dept_parent.name" label="上级部门" >
            </el-table-column>

            <el-table-column label="操作">
                <template slot-scope="scope">
                    <el-button size="small" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                    <el-button type="danger" size="small" @click="handleDel(scope.$index, scope.row)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>

        <!--批量删除-->
        <el-col :span="24" class="toolbar">
            <el-button type="danger" @click="batchRemove" :disabled="this.sels.length===0">批量删除</el-button>
            <el-pagination layout="prev, pager, next" @current-change="handleCurrentChange" :page-size="pageSize" :total="total" style="float:right;">
            </el-pagination>
        </el-col>

        <!--添加和修改界面-->
        <el-dialog :title="title" :visible.sync="addFormVisible" :close-on-click-modal="false">
            <el-form :model="addForm" width="80px" :rules="addFormRules" ref="addForm">
                <el-form-item label="部门" prop="name">
                    <el-input v-model="addForm.name" ></el-input>
                </el-form-item>
                <el-form-item label="部门编号" prop="sn">
                    <el-input v-model="addForm.sn" ></el-input>
                </el-form-item>
                <el-form-item label="是否启用" prop="state">
                    <el-radio-group v-model="addForm.state">
                        <el-radio :label="1">启用</el-radio>
                        <el-radio :label="0">禁用</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="项目经理" prop="managerId">
                    <el-select v-model="addForm.managerId" >
                        <el-option
                                v-for="item in managers"
                                :key="item.id"
                                :label="item.username"
                                :value="item.id">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="上级部门" prop="dept_parent">
                    <el-cascader
                            v-model="addForm.prients"
                            :options="options"
                            :props="{ checkStrictly: true }"
                            clearable></el-cascader>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click.native="addFormVisible = false">取消</el-button>
                 <el-button type="primary" @click.native="addSubmit" :loading="addLoading">提交</el-button>
            </div>
        </el-dialog>
    </section>
</template>
<script>
    export default {
        data(){
            return{
                departments:[],//页面数据
                sels:[],//批量删除
                localPage:1, //当前页
                pageSize:5,//每页条数
                total:0,  //总条数
                listLoading:false,
                //高级查询
                filters:{
                    name:'',
                    state:null
                },
                title:'',     //弹出框标题
                addFormVisible:false,//默认不弹出
                addLoading:false, //添加表单加载
                //添加的数据
                addForm:{
                    id:'',
                    name:'',
                    sn:'',
                    state:'1',
                    managerId:'',
                    prients:[]
                },
                addFormRules: {  //规则
                    name: [
                        { required: true, message: '请填写部门名称', trigger: 'blur' }
                    ],
                    sn: [
                        { required: true, message: '请填写部门编号', trigger: 'blur' }
                    ],
                    managerId: [
                        { required: true, message: '请选择部门经理', trigger: 'change' }
                    ]
                },
                managers:[], //所有经理
                options:[],   //多级部门

            }
        },
        methods:{
            //查询
            getUsers(){
                //给页面赋值第一页
                this.localPage=1;
                this.loadDepartment();//调用页面数据加载方法
            },
            //添加
            handleAdd(){
                //判断有没有 如果有就清空
                if(this.$refs["addForm"]){
                    //清空规则
                    this.$refs["addForm"].resetFields();
                }
                this.title='新增';
                //查找所有部门经理
                this.loademployees();
                //查找多级部门
                this.loadonetwoDept();
                //清空表单
                for(var v in this.addForm){
                    this.addForm[v]=''
                }
                //默认启用
                this.addForm["state"]=1;
                //弹出添加页面
                this.addFormVisible=true;
            },
            //查找所有部门经理选择
            loademployees(){
                this.$http.get("/api/employee").then(res=>{
                    //console.debug(res);
                    this.managers=res.data;
                })
            },
            //查找多级部门
            loadonetwoDept(){
                this.$http.post("/api/dept/getDepartments").then((res)=>{
                    //console.debug(res);
                    //给options赋值
                    this.options=res.data;
                }).catch()
            },
            //提交数据
            addSubmit(){
                // managerId: 329
                //添加一个对象 manager
                this.addForm["manager"]={"id":this.addForm.managerId};
                //如果选择了上级部门
                if(this.addForm.prients){
                    // prients: Array(1)
                    //将多级部门转成字符串
                    var dirPath=this.addForm.prients.toString();
                    //给addfrom添加一个属性
                    this.addForm["dirPath"]=dirPath;

                    //dept_parent
                    //添加dept_parent
                    // prients: Array(1)==>[27,5,8] 取最后一个id
                    var dept_parent=this.addForm.prients[this.addForm.prients.length-1];
                    this.addForm["dept_parent"]={"id":dept_parent};
                    //console.debug(this.addForm);
                }else {
                    //如果没有选择上级部门，就设置为null
                    this.addForm["dirPath"]='';
                    this.addForm["dept_parent"] =null
                }
                //表单的规则
                this.$refs["addForm"].validate((valid) => {
                    //如果符合规则就提交
                    if (valid) {
                        this.$http.put("/api/dept", this.addForm).then(res => {
                            //console.debug(res)
                            let {success, msg} = res.data;
                            if (success) {
                                this.$message({
                                    message: this.title + '成功',
                                    type: 'success'
                                });
                                //关闭弹框
                                this.addFormVisible = false;
                                //重新加载
                                this.loadDepartment();
                            }
                        }).catch()
                    } else {
                        //console.log('error submit!!');
                        return false;
                    }
                })


            },
            //编辑
            handleEdit(index,row){
                //console.debug(row);
                this.title='修改';
                //查找所有部门经理
                this.loademployees();
                //查找多级部门
                this.loadonetwoDept();
                //数据回显
                for(var v in this.addForm){
                    this.addForm[v]=row[v];
                }
                //经理和上级部门要手动回显
                this.addForm.managerId=row.manager.id;
                //如果dirPath不为空，就是有上级目录
                if(row.dirPath){
                    var dirPath=row.dirPath.split(",");
                    //上级部门是一个数组
                    this.addForm.prients=dirPath;
                    //禁用自己选项
                    //var options =  this.getOptions(this.options,dirPath,0);
                    //console.debug(options);
                    //console.debug(this.addForm.prients[0])
                    //this.options=options;
                }
                //判断有没有 如果有就清空
                if(this.$refs["addForm"]){
                    //清空规则
                    this.$refs["addForm"].resetFields();
                }
                //弹出修改框
                this.addFormVisible=true;
            },
            //对 Options 进行递归操作，
            getOptions(val,dirPath,index){
                val.forEach(item => {
                    if (item.value == dirPath[index]) { //如果id是第一级部门

                        if(item==dirPath.length-1){  //找到了选择的上级部门层级
                            console.debug(item);
                            item.disabled = true;  //添加禁用
                            // for(var chird in item.children){  //遍历下级部门
                            //     if(item[chird].value==row.id){   //找到自己
                            //         chird.disabled = true;  //添加禁用
                            //     }
                            // }
                        }else {
                            this.getOptions(item.children,dirPath,index+1); //如果没有找到最后一个层级就调用递归 索引加一
                        }
                    }
                });
                return val ;
            },
            //删除
            handleDel(index,row){
                this.$confirm('确定要删除吗?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(()=>{  //确定删除
                    let uri="/api/dept/"+row.id;
                    //console.debug(uri);
                    this.$http.delete(uri).then((res)=>{
                        let {success,msg} = res.data;
                        if(success){
                            this.$message({
                                message: '删除成功',
                                type: 'success'
                            });
                            //重新加载列表
                            this.loadDepartment();
                        }else {
                            this.$message.error("删除失败");
                        }
                    }).catch()
                }).catch(()=>{  //取消删除
                    this.$message('已取消操作');
                });
            },
            //勾选框
            selsChange(val){
                this.sels=val;
                console.debug(this.sels);
            },
            //批量删除
            batchRemove(){
                //console.debug(this.sels);
                this.$http.post("/api/dept/batchDelete",this.sels).then((res)=>{
                    //console.debug(res)
                    let {success,msg} = res.data
                    if(success){
                        this.$message({
                            message: '删除成功',
                            type: 'success'
                        });
                    }else {
                        this.$message.error(msg);
                    }
                    //重新加载列表
                    this.loadDepartment();
                }).catch()
            },
            //修改当前页
            handleCurrentChange(val){
                this.localPage=val;
                this.loadDepartment();
            },
            //加载列表
            loadDepartment(){
                let param = {
                    localPage: this.localPage,
                    pageSize: this.pageSize,
                    name:this.filters.name,  //高级查询的部门名称
                    state:this.filters.state //高级查询的启用
                };
                this.listLoading = true;
                this.$http.post("/api/dept",param).then((res)=>{
                    //console.debug(res);
                    let {data,totals} = res.data;
                    this.departments=data;
                    this.total=totals;
                    this.listLoading = false;
                }).catch()

            }
        },
        mounted(){
            this.loadDepartment();
        }
    }
</script>