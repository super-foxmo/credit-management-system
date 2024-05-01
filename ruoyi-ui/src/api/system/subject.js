import request from '@/utils/request'

// 查询学科信息列表
export function listSubject(query) {
  return request({
    url: '/system/subject/list',
    method: 'get',
    params: query
  })
}

// 查询学科信息详细
export function getSubject(subjectId) {
  return request({
    url: '/system/subject/' + subjectId,
    method: 'get'
  })
}

// 新增学科信息
export function addSubject(data) {
  return request({
    url: '/system/subject',
    method: 'post',
    data: data
  })
}

// 修改学科信息
export function updateSubject(data) {
  return request({
    url: '/system/subject',
    method: 'put',
    data: data
  })
}

// 删除学科信息
export function delSubject(subjectId) {
  return request({
    url: '/system/subject/' + subjectId,
    method: 'delete'
  })
}
