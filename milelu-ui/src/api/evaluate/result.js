import request from '@/utils/request'

// 查询得分列表
export function listResult(query) {
  return request({
    url: '/evaluate/result/list',
    method: 'get',
    params: query
  })
}

// 查询得分详细
export function getResult(id) {
  return request({
    url: '/evaluate/result/' + id,
    method: 'get'
  })
}

// 新增得分
export function addResult(data) {
  return request({
    url: '/evaluate/result',
    method: 'post',
    data: data
  })
}

// 修改得分
export function updateResult(data) {
  return request({
    url: '/evaluate/result',
    method: 'put',
    data: data
  })
}

// 删除得分
export function delResult(id) {
  return request({
    url: '/evaluate/result/' + id,
    method: 'delete'
  })
}

// 导出得分
export function exportResult(query) {
  return request({
    url: '/evaluate/result/export',
    method: 'get',
    params: query
  })
}