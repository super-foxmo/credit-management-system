import request from '@/utils/request'

// 查询竞赛等级列表
export function listGrade(query) {
  return request({
    url: '/competition/grade/list',
    method: 'get',
    params: query
  })
}

// 查询竞赛等级列表
export function listEnableGrade() {
  return request({
    url: '/competition/grade/listEnable',
    method: 'get',
  })
}

// 查询竞赛等级详细
export function getGrade(gradeId) {
  return request({
    url: '/competition/grade/' + gradeId,
    method: 'get'
  })
}

// 新增竞赛等级
export function addGrade(data) {
  return request({
    url: '/competition/grade',
    method: 'post',
    data: data
  })
}

// 修改竞赛等级
export function updateGrade(data) {
  return request({
    url: '/competition/grade',
    method: 'put',
    data: data
  })
}

// 删除竞赛等级
export function delGrade(gradeId) {
  return request({
    url: '/competition/grade/' + gradeId,
    method: 'delete'
  })
}
