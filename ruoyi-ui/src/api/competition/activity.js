import request from '@/utils/request'

// 查询竞赛信息列表
export function listActivity(query) {
  return request({
    url: '/competition/activity/list',
    method: 'get',
    params: query
  })
}

// 查询状态开启的竞赛信息列表
export function listEnableActivity(query) {
  return request({
    url: '/competition/activity/listEnable',
    method: 'get',
    params: query
  })
}

// 查询竞赛信息详细
export function getActivity(activityId) {
  return request({
    url: '/competition/activity/' + activityId,
    method: 'get'
  })
}

// 新增竞赛信息
export function addActivity(data) {
  return request({
    url: '/competition/activity',
    method: 'post',
    data: data
  })
}

// 修改竞赛信息
export function updateActivity(data) {
  return request({
    url: '/competition/activity',
    method: 'put',
    data: data
  })
}

// 删除竞赛信息
export function delActivity(activityId) {
  return request({
    url: '/competition/activity/' + activityId,
    method: 'delete'
  })
}
