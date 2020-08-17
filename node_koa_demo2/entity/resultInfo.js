const failure = (message) => {
  let result = {
    code: 2,
    message: message
  }
  return result
}

const success = (message, data, total) => {
  let result = {
    code: 1,
    message: message,
    data: data,
    total: total
  }
  return result
}

module.exports = {
  failure,
  success
}
