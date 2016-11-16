/**
 * 根据文件后缀获取对应的小图标
 * @param fn
 * @returns {String}
 */
function extpic(fn){
    var ico = "p1"
    if ((/\.(doc|docx)/i).test(fn)) 
        ico = "d";
    else if ((/\.(xls|xlsx)/i).test(fn)) 
        ico = "x";
    else if ((/\.(ppt|pptx)/i).test(fn)) 
        ico = "p";
    else if ((/\.(txt)/i).test(fn)) 
        ico = "t";
    return ico;
}