 <!--应该是测试的，没有用-->
<template>
  <div style="padding:10px">
    <div style="margin: 10px">
      <el-button type="primary">新增</el-button>
      <el-button type="primary">导出</el-button>
      <el-button type="primary">导入</el-button>
    </div>
<!--   Home-->
    <el-table
        :data="
      tableData.filter(
        (data) =>
          !search || data.name.toLowerCase().includes(search.toLowerCase())
      )
    "
        border  style="width: 100%"
    >
<!--      表格-->
      <el-table-column label="Date" prop="date" />
      <el-table-column label="Name" prop="name" />
      <el-table-column  align="right">
        <template #header>
            <el-input v-model="search" size="mini" placeholder="输入要搜索的关键字" />
        </template>
        <template #default="scope">
          <el-button
              size="mini"
              @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
<!--  二次确认-->
          <el-popconfirm title="确认删除码?">
            <template #reference>
            <el-button
              size="mini"
              type="danger"
              @click="handleDelete(scope.$index, scope.row)">删除</el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <div style="margin: 10px 0">
      <span class="demonstration">Total item count</span>
      <el-pagination
          v-model:currentPage="currentPage"
          :page-sizes="[5, 10, 20]"
          :page-size="10"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
      >
      </el-pagination>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Home',
  components: {
  },
  data(){
    return{
      currentPage:1,
      total:10,
      tableData:[
        {
          date: '2016-05-03',
          name: 'Tom',
          address: 'No. 189, Grove St, Los Angeles',
        },
        {
          date: '2016-05-02',
          name: 'John',
          address: 'No. 189, Grove St, Los Angeles',
        },
        {
          date: '2016-05-04',
          name: 'Morgan',
          address: 'No. 189, Grove St, Los Angeles',
        },
        {
          date: '2016-05-01',
          name: 'Jessy',
          address: 'No. 189, Grove St, Los Angeles',
        },
      ],
      search: '',
    }
  },
  methods:{
    handleEdit(){},
    handleDelete(){},
    handleSizeChange(){},
    handleCurrentChange(){},
  }
}
</script>
