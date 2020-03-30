const archives = [
  {
    "group": "archive",
    "text": "归档",
    "icon": "archive",
    "children": [
      { "text": "四月", "to": "april" },
      { "text": "五月", "to": "may" },
      { "text": "六月", "to": "june" },
      { "text": "七月", "to": "july" }
    ]
  },
  {
    "group": "label",
    "text": "标签",
    "icon": "label",
    "children": [
      { "text": "默认标签", "to": "default" },
      { "text": "Java", "to": "java" },
      { "text": "Android", "to": "android" },
      { "text": "Spring", "to": "spring" }
    ]
  }
]

export default [
  {
    url: '/archives',
    type: 'get',
    response: _ => {
      return {
        total: archives.length,
        items: archives
      }
    }
  }
]