import request from '@/utils/request'

// 查询竞赛报名列表
export function listSignUp(query) {
  return request({
    url: '/competition/signUp/list',
    method: 'get',
    params: query
  })
}

// 根据学生姓名或学号模糊查询去重竞赛报名列表
export function singleListByStudent(query) {
  return request({
    url: '/competition/signUp/singleListByStudent',
    method: 'get',
    params: query
  })
}

// 根据学生姓名或学号模糊查询竞赛报名列表
export function activityListByStudent(query) {
  return request({
    url: '/competition/signUp/activityListByStudent',
    method: 'get',
    params: query
  })
}

// 查询竞赛报名详细
export function getSignUp(signUpId) {
  return request({
    url: '/competition/signUp/' + signUpId,
    method: 'get'
  })
}

// 新增竞赛报名
export function addSignUp(data) {
  return request({
    url: '/competition/signUp',
    method: 'post',
    data: data
  })
}

// 修改竞赛报名
export function updateSignUp(data) {
  return request({
    url: '/competition/signUp',
    method: 'put',
    data: data
  })
}

// 删除竞赛报名
export function delSignUp(signUpId) {
  return request({
    url: '/competition/signUp/' + signUpId,
    method: 'delete'
  })
}
