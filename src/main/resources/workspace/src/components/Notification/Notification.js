import Vue from 'vue';

// 具体的组件
import Notification from './Notification.vue';
Notification.newInstance = properties => {
    const props = properties || {}
    const Instance = new Vue({
        data: props,
        render (h) {
            return h(Notification, {
                props: props
            })
        }
    })
    const component = Instance.$mount()
    document.getElementById('inspire').appendChild(component.$el)
    const notify = Instance.$children[0]
    return {
        show (notiProps) {
            notify.show(notiProps)
        }
    }
}

// 提示单例
let messageInstance;
function getMessageInstance () {
    messageInstance = messageInstance || Notification.newInstance();
    return messageInstance;
}

function notice({color='', mode='',text='',x=null,y='top',timeout=3000}) {
    let instance = getMessageInstance();
    instance.show({
        color:color,
        mode:mode,
        text:text,
        x:x,
        y:y,
        timeout:timeout
    })
}

export default {
    notify (options) {
        return notice(options)
    },
    success () {
        return notice({color:'success', text: '成功！'})
    },
    info(content) {
        return notice({color: 'info', text: content})
    },
    error(content) {
        return notice({color:'error', text: content})
    }
}