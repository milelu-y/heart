import request from '@/utils/request'

// 查询题库列表
export function listSubject(query) {
  return request({
    url: '/evaluate/subject/list',
    method: 'get',
    params: query
  })
}

// 查询题库详细
export function getSubject(id) {
  return request({
    url: '/evaluate/subject/' + id,
    method: 'get'
  })
}

// 新增题库
export function addSubject(data) {
  return request({
    url: '/evaluate/subject',
    method: 'post',
    data: data
  })
}

// 修改题库
export function updateSubject(data) {
  return request({
    url: '/evaluate/subject',
    method: 'put',
    data: data
  })
}

// 删除题库
export function delSubject(id) {
  return request({
    url: '/evaluate/subject/' + id,
    method: 'delete'
  })
}

// 导出题库
export function exportSubject(query) {
  return request({
    url: '/evaluate/subject/export',
    method: 'get',
    params: query
  })
}