import request from '@/utils/request'

// 查询成绩管理表列表
export function listSubjectScore(query) {
  return request({
    url: '/system/subjectScore/list',
    method: 'get',
    params: query
  })
}

// 查询成绩管理表详细
export function getSubjectScore(id) {
  return request({
    url: '/system/subjectScore/' + id,
    method: 'get'
  })
}

// 新增成绩管理表
export function addSubjectScore(data) {
  return request({
    url: '/system/subjectScore',
    method: 'post',
    data: data
  })
}

// 修改成绩管理表
export function updateSubjectScore(data) {
  return request({
    url: '/system/subjectScore',
    method: 'put',
    data: data
  })
}

// 删除成绩管理表
export function delSubjectScore(id) {
  return request({
    url: '/system/subjectScore/' + id,
    method: 'delete'
  })
}
