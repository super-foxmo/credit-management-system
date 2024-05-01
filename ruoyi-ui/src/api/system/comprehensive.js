import request from '@/utils/request'

// 查询综测数据汇总列表
export function listComprehensive(query) {
  return request({
    url: '/system/comprehensive/list',
    method: 'get',
    params: query
  })
}

// 查询综测数据汇总详细
export function getComprehensive(comprehensiveId) {
  return request({
    url: '/system/comprehensive/' + comprehensiveId,
    method: 'get'
  })
}

// 新增综测数据汇总
export function addComprehensive(data) {
  return request({
    url: '/system/comprehensive',
    method: 'post',
    data: data
  })
}

// 修改综测数据汇总
export function updateComprehensive(data) {
  return request({
    url: '/system/comprehensive',
    method: 'put',
    data: data
  })
}

// 删除综测数据汇总
export function delComprehensive(comprehensiveId) {
  return request({
    url: '/system/comprehensive/' + comprehensiveId,
    method: 'delete'
  })
}
