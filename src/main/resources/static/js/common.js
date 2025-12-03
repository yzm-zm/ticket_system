// 公共JS函数

// 配置axios基础URL
axios.defaults.baseURL = 'http://localhost:8080';

// 请求拦截器
axios.interceptors.request.use(
    function(config) {
        // 可以在这里添加token等
        return config;
    },
    function(error) {
        return Promise.reject(error);
    }
);

// 响应拦截器
axios.interceptors.response.use(
    function(response) {
        return response;
    },
    function(error) {
        if (error.response) {
            console.error('请求错误:', error.response.data);
        } else {
            console.error('网络错误:', error.message);
        }
        return Promise.reject(error);
    }
);

// 格式化日期时间
function formatDateTime(dateTime) {
    if (!dateTime) return '';
    let dateStr = String(dateTime);
    // 替换 T 为空格
    dateStr = dateStr.replace('T', ' ');
    // 如果包含 . 则截取到秒
    if (dateStr.indexOf('.') > 0) {
        dateStr = dateStr.substring(0, dateStr.indexOf('.'));
    }
    return dateStr;
}

// 格式化日期
function formatDate(date) {
    if (!date) return '';
    return date.split(' ')[0];
}

// 格式化金额
function formatMoney(amount) {
    if (!amount) return '0.00';
    return parseFloat(amount).toFixed(2);
}

// 订单状态映射
const orderStatusMap = {
    0: '待支付',
    1: '已支付',
    2: '已退票',
    3: '已改签'
};

// 座位状态映射
const seatStatusMap = {
    1: '正常',
    0: '已退票',
    2: '已改签'
};

// 通用状态映射
const statusMap = {
    1: '启用',
    0: '禁用'
};

