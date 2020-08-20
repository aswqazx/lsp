export class ResultInfo {
    static failure(message: any) {
        return {
            code: 2,
            message: message
        };
    }
    static success(message: any, data: any, total: any) {
        return {
            code: 1,
            message: message,
            data: data,
            total: total
        };
    }
}