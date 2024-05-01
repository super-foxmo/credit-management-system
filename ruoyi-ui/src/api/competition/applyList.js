import request from '@/utils/request'

// 查询报名列表列表
export function listApplyList(query) {
  return request({
    url: '/competition/applyList/list',
    method: 'get',
    params: query
  })
}

// 查询报名列表详细
export function getApplyList(signUpId) {
  return request({
    url: '/competition/applyList/' + signUpId,
    method: 'get'
  })
}

// 新增报名列表
export function addApplyList(data) {
  return request({
    url: '/competition/applyList',
    method: 'post',
    data: data
  })
}

// 修改报名列表
export function updateApplyList(data) {
  return request({
    url: '/competition/applyList',
    method: 'put',
    data: data
  })
}

// 删除报名列表
export function delApplyList(signUpId) {
  return request({
    url: '/competition/applyList/' + signUpId,
    method: 'delete'
  })
}
