import { markAsRead, markAsDelete } from '@/api/comment'
import Notification from "@/components/Notification/Notification";

export default {
  methods: {
    readComment(comment) {
      markAsRead(comment.id).then(() => {
        let idx = this.comments.indexOf(comment);
        this.comments.splice(idx, 1);
        Notification.success()
      })
    },
    deleteComment(comment) {
      markAsDelete(comment.id).then(() => {
        let idx = this.comments.indexOf(comment);
        this.comments.splice(idx, 1);
        Notification.success()
      })
    }
  }
}