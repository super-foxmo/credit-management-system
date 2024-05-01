import request from '@/utils/request'

// 查询综测数据汇总列表
export function listScoreEvaluation(query) {
  return request({
    url: '/system/scoreEvaluation/list',
    method: 'get',
    params: query
  })
}

// 查询综测数据汇总列表D
export function selectScoreEvaluationData(query) {
  return request({
    url: '/system/scoreEvaluation/evaluationData',
    method: 'get',
    params: query
  })
}

// 查询综测数据汇总详细
export function getScoreEvaluation(comprehensiveId) {
  return request({
    url: '/system/scoreEvaluation/' + comprehensiveId,
    method: 'get'
  })
}

// 新增综测数据汇总
export function addScoreEvaluation(data) {
  return request({
    url: '/system/scoreEvaluation',
    method: 'post',
    data: data
  })
}

// 修改综测数据汇总
export function updateScoreEvaluation(data) {
  return request({
    url: '/system/scoreEvaluation',
    method: 'put',
    data: data
  })
}

// 删除综测数据汇总
export function delScoreEvaluation(comprehensiveId) {
  return request({
    url: '/system/scoreEvaluation/' + comprehensiveId,
    method: 'delete'
  })
}
