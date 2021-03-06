<template>
  <div class="createPost-container">
    <el-form ref="postForm" :model="postForm" :rules="rules" class="form-container">
      <sticky :z-index="10" :class-name="'sub-navbar '+postForm.status">
        <el-button
          v-loading="loading"
          style="margin-left: 10px;"
          type="success"
          @click="submitForm"
          v-permission="['sys:sysauthorities:push']"
        >发布</el-button>
        <el-button
          v-loading="loading"
          type="warning"
          v-permission="['sys:sysauthorities:save']"
          @click="draftForm"
        >确认</el-button>
      </sticky>

      <div class="createPost-main-container">
        <el-form-item style="margin-bottom: 40px;" prop="name" label-width="90px" label="名称:">
          <el-input
            v-model="postForm.name"
            :rows="1"
            type="textarea"
            class="article-textarea"
            autosize
            placeholder="请填写名称"
          />
        </el-form-item>
        <el-form-item style="margin-bottom: 40px;" prop="cnName" label-width="90px" label="中文名称:">
          <el-input
            v-model="postForm.cnName"
            :rows="1"
            type="textarea"
            class="article-textarea"
            autosize
            placeholder="请填写中文名称"
          />
        </el-form-item>
        <el-form-item
          style="margin-bottom: 40px;"
          prop="authorities"
          label-width="90px"
          label="SPRING权限名称:"
        >
          <el-input
            v-model="postForm.authorities"
            :rows="1"
            type="textarea"
            class="article-textarea"
            autosize
            placeholder="请填写SPRING权限名称"
          />
        </el-form-item>
        <el-form-item style="margin-bottom: 40px;" prop="isSys" label-width="90px" label="是否系统权限:">
          <el-input
            v-model="postForm.isSys"
            :rows="1"
            type="textarea"
            class="article-textarea"
            autosize
            placeholder="请填写是否系统权限"
          />
        </el-form-item>
      </div>
    </el-form>
  </div>
</template>

<script>
import permission from "@/directive/permission/index.js"; // 权限判断指令
import checkPermission from "@/utils/permission"; // 权限判断函数
import Tinymce from "@/components/Tinymce";
import Upload from "@/components/Upload/SingleImage3";
import Sticky from "@/components/Sticky"; // 粘性header组件
import {
  findByIdSysAuthorities,
  createSysAuthorities,
  updateSysAuthorities,
  pushSysAuthorities
} from "@/api/sys/SysAuthoritiesApi";
import { searchUser } from "@/api/remote-search";

const defaultForm = {
  id: undefined,
  name: "",
  cnName: "",
  authorities: "",
  isSys: ""
};

export default {
  name: "SysAuthoritiesDetail",
  components: { Tinymce, Upload, Sticky },
  directives: { permission },
  props: {
    isEdit: {
      type: Boolean,
      default: false
    }
  },
  data() {
    const validateRequire = (rule, value, callback) => {
      if (value === "") {
        this.$message({
          message: rule.field + "为必传项",
          type: "error"
        });
        callback(new Error(rule.field + "为必传项"));
      } else {
        callback();
      }
    };
    return {
      postForm: Object.assign({}, defaultForm),
      loading: false,
      userListOptions: [],
      rules: {
        name: [{ validator: validateRequire }],
        cnName: [{ validator: validateRequire }],
        authorities: [{ validator: validateRequire }],
        isSys: [{ validator: validateRequire }]
      },
      tempRoute: {}
    };
  },
  computed: {},
  created() {
    if (this.isEdit) {
      const id = this.$route.params && this.$route.params.id;
      this.fetchData(id);
    } else {
      this.postForm = Object.assign({}, defaultForm);
    }

    // Why need to make a copy of this.$route here?
    // Because if you enter this page and quickly switch tag, may be in the execution of the setTagsViewTitle function, this.$route is no longer pointing to the current page
    // https://github.com/PanJiaChen/vue-element-admin/issues/1221
    this.tempRoute = Object.assign({}, this.$route);
  },
  methods: {
    fetchData(id) {
      findByIdSysAuthorities({ id: id })
        .then(response => {
          this.postForm = response.data;
          // set tagsview title
          this.setTagsViewTitle();
          // set page title
          this.setPageTitle();
        })
        .catch(err => {
          console.log(err);
        });
    },
    createData(data) {
      createSysAuthorities(data)
        .then(response => {
          this.$notify({
            title: "成功",
            message: "保存成功",
            type: "success",
            duration: 2000
          });
        })
        .catch(err => {
          console.log(err);
        });
    },
    updateData(data) {
      updateSysAuthorities(data)
        .then(response => {})
        .catch(err => {
          console.log(err);
        });
    },
    pushData(data) {
      pushSysAuthorities(data)
        .then(response => {})
        .catch(err => {
          console.log(err);
        });
    },
    setTagsViewTitle() {
      const title = "编辑-系统权限";
      const route = Object.assign({}, this.tempRoute, {
        title: `${title}-${this.postForm.cnName}`
      });
      this.$store.dispatch("tagsView/updateVisitedView", route);
    },
    setPageTitle() {
      const title = "编辑-系统权限";
      document.title = `${title} - ${this.postForm.cnName}`;
    },
    submitForm() {
      this.$refs.postForm.validate(valid => {
        if (valid) {
          debugger;
          console.log(this.postForm);
          this.loading = true;
          this.pushData(this.postForm);
          this.loading = false;
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    draftForm() {
      this.$refs.postForm.validate(valid => {
        if (valid) {
          debugger;
          console.log(this.postForm);
          this.loading = true;
          if (!this.isEdit) {
            this.createData(this.postForm);
          } else {
            this.updateData(this.postForm);
          }
          this.loading = false;
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    getRemoteUserList(query) {
      searchUser(query).then(response => {
        if (!response.data.items) return;
        this.userListOptions = response.data.items.map(v => v.name);
      });
    }
  }
};
</script>

<style lang="scss" scoped>
@import "~@/styles/mixin.scss";

.createPost-container {
  position: relative;

  .createPost-main-container {
    padding: 40px 45px 20px 50px;

    .postInfo-container {
      position: relative;
      @include clearfix;
      margin-bottom: 10px;

      .postInfo-container-item {
        float: left;
      }
    }
  }

  .word-counter {
    width: 40px;
    position: absolute;
    right: 10px;
    top: 0px;
  }
}

.article-textarea /deep/ {
  textarea {
    padding-right: 40px;
    resize: none;
    border: none;
    border-radius: 0px;
    border-bottom: 1px solid #bfcbd9;
  }
}
</style>
