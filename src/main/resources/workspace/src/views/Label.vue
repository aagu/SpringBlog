<template>
  <div>
    <v-container>
      <v-flex>
        <v-card-text style="flex: 1 0 auto">
          <v-btn
            rounded
            depressed
            @click.stop="dialog = true"
          >
            <v-icon>add</v-icon>
            Add
          </v-btn>
          <v-btn
            rounded
            depressed
            @click="edit = !edit"
          >
            <v-icon v-if="!edit">edit</v-icon>
            <v-icon v-else>done</v-icon>
            <span v-if="!edit">edit</span>
            <span v-else>done</span>
          </v-btn>
          <v-chip
            class="ma-2"
            v-for="label in labels"
            :key="label.id"
            :close="edit"
            @click:close="handleDelete(label)"
          >
            <span :contenteditable="edit" v-html="label.name" @blur="handleEdit($event,label)"></span>
          </v-chip>
        </v-card-text>
      </v-flex>

      <v-dialog
        v-model="dialog"
        max-width="290"
      >
        <v-card>
          <v-card-title>
            <span class="headline">Add Label</span>
          </v-card-title>
          <v-card-text>
            <v-container>
              <v-text-field label="Label Name" v-model="newLabelName" required></v-text-field>
            </v-container>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="blue darken-1" text @click="dialog = false">Close</v-btn>
            <v-btn color="blue darken-1" text @click="handleAdd">Save</v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-container>
  </div>
</template>

<script>
  import {addLabel, deleteLabel, editLabel, getLabels} from '@/api/label'
  import Notification from '@/components/Notification/Notification'

  export default {
    data: () => ({
      dialog: false,
      labels: [],
      isLoading: false,
      initKey: 100,
      edit: false,
      newLabelName: '',
      added: [],
      removed: [],
      modified: []
    }),

    mounted() {
      this.fetch()
    },

    methods: {
      fetch () {
        if (this.labels.length) return

        getLabels().then(resp => {
          this.labels = resp.data.data.items
        })
      },
      handleAdd() {
        let newLabel = {name: this.newLabelName}
        addLabel(newLabel).then(resp => {
          this.labels.push(resp.data.data)
          this.newLabelName = ''
          this.dialog = false
          Notification.notify({color:'success', text: '添加成功！'})
        })
      },
      handleDelete(label) {
        deleteLabel(label.id).then(() => {
          let index = this.labels.indexOf(label)
          if (index >= 0) {
            this.labels.splice(index, 1)
            Notification.notify({color:'info', text: '删除成功！'})
          }
        })
      },
      handleEdit(event, label) {
        label.name = event.target.innerText
        editLabel(label).then(() => {
          Notification.notify({color:'info', text: '修改成功！'})
        })
      },
      save() {

      }
    },
  }
</script>