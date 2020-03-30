const desserts = [
  {
    name: '备份和恢复',
    info: 'com.huawei.KoBackup/com.huawei.KoBackup.InitializeActivity',
    times: 6
  },
  {
    name: '备忘录',
    info: 'com.example.android.notepad/com.example.android.notepad.NotePadActivity',
    times: 9
  },
  {
    name: '崩坏3',
    info: 'com.miHoYo.bh3.huawei/com.miHoYo.bh3.huawei.HuaweiACT',
    times: 16
  },
  {
    name: '哔哩哔哩',
    info: 'com.bilibili.app.in/tv.danmaku.bili.ui.splash.SplashActivity',
    times: 3
  },
  {
    name: '查找我的手机',
    info: 'com.huawei.android.findmyphone/com.huawei.android.findmyphone.ui.FindMyPhoneActivity',
    times: 16
  },
  {
    name: '服务',
    info: 'com.huawei.phoneservice/com.huawei.phoneservice.LaunchActivity',
    times: 1
  },
  {
    name: '广西农信',
    info: 'com.nxy.mobilebank.gx/com.nxy.mobilebank.activity.SplashActivity',
    times: 2
  },
  {
    name: '哈啰出行',
    info: 'com.jingyao.easybike/com.hellobike.atlas.business.splash.SplashActivity',
    times: 3
  },
  {
    name: '和掌桂',
    info: 'com.sinovatech.gxmobile.ui/com.sinovatech.gxmobile.ui.WelcomeActivity',
    times: 25
  },
  {
    name: '花粉俱乐部',
    info: 'com.huawei.fans/com.huawei.fans.SplashActivity',
    times: 26
  }
]

let firstTime = true

export default [
  {
    url: '/request/list',
    type: 'get',
    response: _ => {
      return {
        code: 200,
        data: {
          total: desserts.length,
          items: desserts
        }
      }
    }
  },

  {
    url: `/request/gen/[A-Za-z0-9]`,
    type: 'post',
    response: {
      code: 200,
      data: {
        message: 'created'
      }
    }
  },

  {
    url: `/request/query/[A-Za-z0-9]`,
    type: 'get',
    response: _ => {
      if (firstTime) {
        firstTime = false
        return {
          code: 200,
          data: {
            message: 'working'
          }
        }
      }

      firstTime = true
      return {
        code: 200,
        data: {
          message: 'ok'
        }
      }
    }
  }
]

