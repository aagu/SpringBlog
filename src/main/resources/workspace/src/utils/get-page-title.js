const title = 'aagu\'s blog'

export default function getPageTitle(pageTitle) {
  if (pageTitle !== title) {
    return `${pageTitle} - ${title}`
  } else {
    return `${title}`
  }
}