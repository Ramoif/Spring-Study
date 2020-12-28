/*这个文件必须放在根目录下。
（所以控制台报错Module not found: Error: Can't resolve './src' in ...）*/
module.exports = {
    /*下面是两个核心配置，entry是程序入口*/
    entry: './modules/main.js',
    output:{
        filename:"./js/bundle.js"
    },
};