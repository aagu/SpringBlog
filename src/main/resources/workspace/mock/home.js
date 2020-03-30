import Mock from 'mockjs'

const articleList = []

for (let i = 0; i < 10; i++) {
  articleList.push(Mock.mock({
      id: '@increment',
      title: '@title(2, 5)',
      content: '@sentence(4, 6)',
      author: '@name',
      date: '@datetime',
      url: '/detail/'
  }))
}

articleList.forEach(item => {
  item['url'] = '/detail/' + item['id']
})

export default [
    {
      url: '/blog',
      type: 'get',
      response: config => {
        const { page, limit } = config.query
        const pageList = articleList.filter((item, index) => index < limit * page && index >= limit * (page - 1))
        return {
          articles: pageList,
          labels: null,
          pages: 3,
          currePage: page,
          archiveLabel: null,
          curreLabel: 'default',
          curreKeyWord: ''
        }
      }
    },
    {
      url: `/detail/[0-9]+`,
      type: 'get',
      response: config => {
        const id = config.url.split('/')[3]
        const article = articleList.filter((item, index) => index == 1)
        const { _, title, content, author, date, url} = article
        return {
          article
        }
      }
    },
    {
      url: '/article/list',
      type: 'get',
      response: config => {
        const { page, limit } = config.query
        const pageList = articleList.filter((item, index) => index < limit * page && index >= limit * (page - 1))
        return {
          articles: pageList,
          pages: 3,
          currePage: page,
        }
      }
    }
  ]