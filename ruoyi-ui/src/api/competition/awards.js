import request from '@/utils/request'

// 查询竞赛获奖列表
export function listAwards(query) {
  return request({
    url: '/competition/awards/list',
    method: 'get',
    params: query
  })
}

// 查询竞赛获奖详细
export function getAwards(awardsId) {
  return request({
    url: '/competition/awards/' + awardsId,
    method: 'get'
  })
}

// 新增竞赛获奖
export function addAwards(data) {
  return request({
    url: '/competition/awards',
    method: 'post',
    data: data
  })
}

// 修改竞赛获奖
export function updateAwards(data) {
  return request({
    url: '/competition/awards',
    method: 'put',
    data: data
  })
}

// 删除竞赛获奖
export function delAwards(awardsId) {
  return request({
    url: '/competition/awards/' + awardsId,
    method: 'delete'
  })
}
