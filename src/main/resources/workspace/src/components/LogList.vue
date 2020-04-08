<template>
  <v-card>
    <v-toolbar flat dense color="transparent">
      <v-toolbar-title>系统日志</v-toolbar-title>
    </v-toolbar>
    <v-divider></v-divider>
    <v-card-text>
      <v-simple-table>
        <template v-slot:default>
          <tbody>
            <tr v-for="item in logs" :key="item.id">
              <td>{{ item.time | parseTime }}</td>
              <td>{{ item.info }}</td>
            </tr>
          </tbody>
        </template>
      </v-simple-table>
    </v-card-text>
  </v-card>
</template>

<script>
  import {getLogs} from '@/api/admin'

  export default {
  name: 'LogList',
  data() {
    return {
      logs: []
    }
  },
  mounted() {
    this.fetch()
  },
  methods: {
    fetch() {
      getLogs({page: 1, limit: 5}).then(resp => {
        this.logs = resp.data
      })
    }
  }
}
</script>