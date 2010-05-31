
var loc = locator;
var attr = null;
var isattr = false;
var inx = locator.lastIndexOf('@');
if(inx != -1){
    loc = locator.substring(0, inx);
    attr = locator.substring(inx + 1);
    isattr = true;
}
var found = $(inDocument).find(loc);
if(found.length == 1 ){
    if(isattr){
        return found[0].attr(attr);
    }else{
        return found[0];
    }
} else if(found.length > 1){
    if(isattr){
        return found.get().attr(attr);
    }else{
        return found.get();
    }
}else{
    return null;
}
