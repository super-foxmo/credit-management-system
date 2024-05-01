import request from '@/utils/request'

// 查询专业信息列表
export function listSpecialty(query) {
  return request({
    url: '/structure/specialty/list',
    method: 'get',
    params: query
  })
}

// 查询状态开启的专业信息列表
export function listEnableSpecialty(query) {
  return request({
    url: '/structure/specialty/listEnable',
    method: 'get',
    params: query
  })
}

// 查询专业信息详细
export function getSpecialty(specialtyId) {
  return request({
    url: '/structure/specialty/' + specialtyId,
    method: 'get'
  })
}

// 新增专业信息
export function addSpecialty(data) {
  return request({
    url: '/structure/specialty',
    method: 'post',
    data: data
  })
}

// 修改专业信息
export function updateSpecialty(data) {
  return request({
    url: '/structure/specialty',
    method: 'put',
    data: data
  })
}

// 删除专业信息
export function delSpecialty(specialtyId) {
  return request({
    url: '/structure/specialty/' + specialtyId,
    method: 'delete'
  })
}