import request from '@/utils/request'

// 查询题库分类列表
export function listCategory(query) {
  return request({
    url: '/evaluate/category/list',
    method: 'get',
    params: query
  })
}

// 查询题库分类详细
export function getCategory(id) {
  return request({
    url: '/evaluate/category/' + id,
    method: 'get'
  })
}

// 新增题库分类
export function addCategory(data) {
  return request({
    url: '/evaluate/category',
    method: 'post',
    data: data
  })
}

// 修改题库分类
export function updateCategory(data) {
  return request({
    url: '/evaluate/category',
    method: 'put',
    data: data
  })
}

// 删除题库分类
export function delCategory(id) {
  return request({
    url: '/evaluate/category/' + id,
    method: 'delete'
  })
}

// 导出题库分类
export function exportCategory(query) {
  return request({
    url: '/evaluate/category/export',
    method: 'get',
    params: query
  })
}