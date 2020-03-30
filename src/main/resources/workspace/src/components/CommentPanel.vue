<template>
  <v-row>
    <v-col
      md="10"
      offset-md="1"
    >
      <v-card
        class="mx-auto"
      >
        <v-subheader>评论</v-subheader>

        <v-list three-line>
          <template v-for="(item, index) in comments">
            <v-list-item
              :key="index"
            >
              <v-list-item-content>
                <v-list-item-title>{{ item.email }}</v-list-item-title>
                <v-list-item-subtitle>{{item.detail}}</v-list-item-subtitle>
              </v-list-item-content>

              <v-list-item-action>
                <v-list-item-action-text>{{ item.date || parseTime }}</v-list-item-action-text>
                <v-icon color="cyan">reply</v-icon>
              </v-list-item-action>
            </v-list-item>
            <v-divider
              :key="`divider-${index}`"
            ></v-divider>
          </template>
        </v-list>

        <v-card
          outlined
          style="marginTop: -10px;"
        >
          <v-subheader>说点什么</v-subheader>
          <v-card-text>
            <v-form
              ref="commentForm"
              lazy-validation
            >
              <v-text-field
                prepend-icon="mail"
                outlined
                type="email"
                :rules="emailRules"
                v-model="newComment.email"
                label="邮箱"
                required
              ></v-text-field>
              <v-textarea
                outlined
                name="comment"
                label="内容"
                v-model="newComment.detail"
              ></v-textarea>
            </v-form>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="primary" @click="sendComment">
              <v-icon>send</v-icon> 发送
            </v-btn>
          </v-card-actions>
        </v-card>

        <div class="text-center">
          <v-pagination
            circle
            :value="currentPage"
            @next="goNext"
            @previous="goPrev"
            :length="totalPage > 5? 5: totalPage"
          ></v-pagination>
        </div>
      </v-card>
    </v-col>
  </v-row>
</template>

<script>
  import { postComment } from "@/api/comment";
  import { deepClone } from "@/utils";
  import Notification from '@/components/Notification/Notification'

  export default {
    name: 'Comment',
    props: {
      comments: {},
      articleId: Number,
      currentPage: Number,
      totalPage: Number
    },
    data: () => ({
      newComment: {
        email: '',
        detail: '',
        articleId: -1
      },
      emailRules: [
        v => !!v || 'E-mail is required',
        v => /.+@.+/.test(v) || 'E-mail must be valid',
      ],
    }),
    methods: {
      sendComment() {
        this.newComment.articleId = this.articleId
        this.newComment.date = Number(new Date())
        postComment(this.newComment).then(() => {
          Notification.info('评论成功')
          this.$emit('addComment', deepClone(this.newComment))
          this.reset()
        })
      },
      reset() {
        this.newComment = {
          email: '',
          detail: '',
          articleId: -1
        }
        this.$refs.commentForm.reset()
        this.$refs.commentForm.resetValidation()
      },
      goNext() {
        this.$emit('next')
      },
      goPrev() {
        this.$emit('prev')
      }
    }
  }
</script>