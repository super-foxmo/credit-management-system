import request from '@/utils/request'

// 查询竞赛报名列表
export function listApply(query) {
  return request({
    url: '/competition/apply/list',
    method: 'get',
    params: query
  })
}

// 查询竞赛报名详细
export function getApply(activityId) {
  return request({
    url: '/competition/apply/' + activityId,
    method: 'get'
  })
}

// 竞赛报名
export function activityParticipation(activityId) {
  return request({
    url: '/competition/apply/participation/' + activityId,
    method: 'get'
  })
}

// 新增竞赛报名
export function addApply(data) {
  return request({
    url: '/competition/apply',
    method: 'post',
    data: data
  })
}

// 修改竞赛报名
export function updateApply(data) {
  return request({
    url: '/competition/apply',
    method: 'put',
    data: data
  })
}

// 删除竞赛报名
export function delApply(activityId) {
  return request({
    url: '/competition/apply/' + activityId,
    method: 'delete'
  })
}
