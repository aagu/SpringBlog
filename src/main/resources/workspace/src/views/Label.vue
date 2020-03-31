<template>
  <div>
    <v-container>
      <v-flex>
        <v-row no-gutters style="flex-shrink: 0">
          <v-col align="center" cols="4">
            <v-btn
              @click="fetch"
            >
              <v-icon>undo</v-icon>
              Reset
            </v-btn>
          </v-col>
          <v-spacer></v-spacer>
          <v-col align="center" cols="4">
            <v-btn
              class="white--text"
              color="green darken-1"
              @click="save"
            >
              Save
              <v-icon right>save</v-icon>
            </v-btn>
          </v-col>
        </v-row>
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
  import { getLabels } from '@/api/label'

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
        this.labels.push({id: this.initKey++, name: this.newLabelName})
        this.added.push({name: this.newLabelName})
        this.newLabelName = ''
        this.dialog = false
      },
      handleDelete(label) {
        let index = this.labels.indexOf(label)
        if (index >= 0) {
          this.labels.splice(index, 1)
        }
        this.removed.push(label)
      },
      handleEdit(event, label) {
        label.name = event.target.innerText
        this.modified.push(label)
      },
      save() {

      }
    },
  }
</script>