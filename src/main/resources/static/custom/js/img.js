// 获取图片真实高度
function getImageWidth(url, callback) {
    var img = new Image();
    img.src = url;
    // 如果图片被缓存，则直接返回缓存数据
    if (img.complete) {
        callback(img.width, img.height);
    } else {
        img.onload = function () {
            callback(img.width, img.height);
        }
    }
}