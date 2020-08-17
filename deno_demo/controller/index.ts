export default {
    getIndex: async (
        { response, request }: { response: any, request: any },
        next: Function,
    ) => {
        response.status = 200;
        response.body = {
            code: 1,
            message: 'this is index',
            data: null
        };
    }
};
