<%@ page pageEncoding="utf-8" contentType="text/html;charset=UTF-8"	language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

	</div class="col-xs-12 col-sm-10 hidden-sm" >
	</div class="col-xs-12 col-sm-10 visible-xs" >
</div>
 <div class="modal fade" id="enlargeImageModal" tabindex="-1" role="dialog" aria-labelledby="enlargeImageModal" aria-hidden="true">
    <div class="modal-dialog modal-lg" role="document">
      <div class="modal-content">
        <div class="modal-header" style="text-align:center">
           <b id="enalrageModalTitle" style="font-size:1.2vw"></b>
           <button type="button" class="close" data-dismiss="modal" ><i class="fa fa-close fa-2x" aria-hidden="true"></i></button>
           <br>
        </div>
        <div class="modal-body text-center">
          <img src="" class="enlargeImageModalSource" style="width: 100%;max-width:750px;">
        </div>
      </div>
    </div>
</div>

<div class="footer text-center hidden-xs hidden-sm">
	<div class="box" style="width: 6%"></div>
	<div class="box" style="width: 88%"></div>
	<div class="box" style="width: 6%; padding: 0.5vh;"><img src="${contextPath}/images/logo_gsil.png" style="height:100%; width: 100%;"></div>   				
</div>
</body>
</html>

