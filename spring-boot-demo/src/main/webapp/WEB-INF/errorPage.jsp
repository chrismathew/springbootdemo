<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
<head>

<link rel="stylesheet" type="text/css" 	href="/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" 	href="/css/custom.css" />


<spring:url value="/css/bootstrap.min.css" var="springCss" />
	<link href="${springCss}" rel="stylesheet" />
	
<c:url value="/css/custom.css" var="jstlCss" />
<link href="${jstlCss}" rel="stylesheet" />

</head>

<body>

<div class="container">

	<div class="jumbotron text-center">

	
	 <c:set var = "errorCode" scope = "request" value = '<%=request.getAttribute("errorCode")%>' />
 
    
      <c:choose>
         
         <c:when test = "${errorCode == 'CONTRACT_LOCKED'}">
           <h2>Contract is locked
         </c:when>
         
         <c:when test = "${errorCode ==  'MEMBER_NOT_FOUND'}">
            <h2>Member is not found</h2>
         </c:when>
          <c:when test = "${errorCode ==  'MEMBER_NULLDOB'}">
            <h2>Member DOB is null</h2>
         </c:when>
          <c:when test = "${errorCode ==  'MEMBER_INACTIVE'}">
            <h2>Member is inactive.</h2>
         </c:when>
          <c:when test = "${errorCode ==  'unexpected.error '}">
            <h2>Unexpected error</h2>
         </c:when>
          <c:when test = "${errorCode ==  'SSO.ERROR.INVALID.MSG '}">
            <h2>Invalid message</h2>
         </c:when>
          <c:when test = "${errorCode ==  'pendingaccount'}">
            <h2>Pending account</h2>
         </c:when>
         <c:when test = "${errorCode ==  'planUser'}">
            <h2>Plan user</h2>
         </c:when>
          <c:when test = "${errorCode ==  'InvalidAccess'}">
           <h2>Invalid access</h2>
         </c:when>
         <c:otherwise>
            
         </c:otherwise>
      </c:choose>
  		</div>

	</div>
	<!-- /.container -->    
  <script type="text/javascript" src="/libs/jquery.js"> </script>
   <script type="text/javascript" src="/libs/bootstrap.min.js"></script>
</body>

</html>
