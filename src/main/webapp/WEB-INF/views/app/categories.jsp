<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="../header.jspf" %>

<!-- Begin Page Content -->
<div class="container-fluid">
    <!-- Page Heading -->
    <form:form modelAttribute="user" method="post">
    <div class="d-sm-flex align-items-center justify-content-between mb-4">
        <div class="row">
            <h1 class="h3 mb-0 text-gray-800">User categories</h1>
        </div>
        <div class="row">
            <button type="submit" class="d-none d-inline-block btn btn-primary shadow">
                <i class="fas fa-paper-plane text-white-50"></i> Submit </button>
        </div>
    </div>

        <div class="text-center">
            <form:errors path="favoriteCategories" cssClass="lead text-danger mb-5" element="div" />

            <c:forEach items="${user.favoriteCategories}" var="category" varStatus="s">
                <spring:bind path="favoriteCategories[${s.index}].id">
                    <c:if test="${status.error}">
                    <span class="lead text-danger mb-5">
                            ${status.errorMessage} for value ${category.id}
                    </span> <br>
                    </c:if>
                </spring:bind>
            </c:forEach>

        </div>
    <div class="row">
        <c:forEach items="${categories}" var="category" varStatus="s">
        <div class="col-xl-3 col-md-6 mb-4">
            <div class="card bg-dark text-white shadow" onclick="checkCategory(this)" onload="setColor(this)">
                <div class="card-body">
                    <p hidden><form:checkbox path="favoriteCategories" value="${category.id}"/></p>
                        ${category.name}
                    <div class="text-white-50 small">${category.id}</div>
                </div>
            </div>
        </div>
        <c:if test="${s.count % 4 == 0}">
            </div>
            <div class="row">
        </c:if>
        </c:forEach>
        </div>

        </form:form>
</div>
<!-- /.container-fluid -->

<script type="text/javascript">
    let categoryList = ${user.favoriteCategories.stream().map(c->"{ id: ".concat(c.id).concat(", name: '").concat(c.name).concat("'}")).toList()};

    document.querySelectorAll("input[type='checkbox']")
        .forEach(i=>{
            if(categoryList.map(c=>parseInt(c.id)).includes(parseInt(i.value))){
                i.checked = true;
                setColor(i.parentElement.parentElement.parentElement);
            }
    });

    function checkCategory(div){
        let input = div.childNodes[1].childNodes[1].childNodes[0];
        input.checked = !input.checked;
        setColor(div);
    }
    function setColor(div){
        let input = div.childNodes[1].childNodes[1].childNodes[0];
        if(input.checked){
            div.classList.remove("bg-dark");
            div.classList.add("bg-primary");
        } else {
            div.classList.add("bg-dark");
            div.classList.remove("bg-primary");
        }
    }
</script>

<%@ include file="../footer.jspf" %>