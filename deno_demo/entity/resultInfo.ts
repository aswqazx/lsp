class ResultInfo {
    static failure(message: any) {
        let result = {
            code: 2,
            message: message
        };
        return result;
    }
    static success(message: any, data: any, total: any) {
        let result = {
            code: 1,
            message: message,
            data: data,
            total: total
        };
        return result;
    }
}

export default ResultInfo;
