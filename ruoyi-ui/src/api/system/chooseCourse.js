import request from '@/utils/request'

// 查询选课中心列表
export function listChooseCourse(query) {
  return request({
    url: '/system/chooseCourse/list',
    method: 'get',
    params: query
  })
}

// 查询选课中心详细
export function getChooseCourse(subjectId) {
  return request({
    url: '/system/chooseCourse/' + subjectId,
    method: 'get'
  })
}

// 新增选课中心
export function addChooseCourse(data) {
  return request({
    url: '/system/chooseCourse',
    method: 'post',
    data: data
  })
}

// 新增选课中心
export function appendCourse(data) {
  return request({
    url: '/system/chooseCourse',
    method: 'post',
    data: data
  })
}

// 修改选课中心
export function updateChooseCourse(data) {
  return request({
    url: '/system/chooseCourse',
    method: 'put',
    data: data
  })
}

// 删除选课中心
export function delChooseCourse(subjectId) {
  return request({
    url: '/system/chooseCourse/' + subjectId,
    method: 'delete'
  })
}
