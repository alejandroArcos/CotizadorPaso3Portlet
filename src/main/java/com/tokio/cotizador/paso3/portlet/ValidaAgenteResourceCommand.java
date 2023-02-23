package com.tokio.cotizador.paso3.portlet;

import com.google.gson.Gson;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.tokio.cotizador.paso3.constants.CotizadorPaso3PortletKeys;
import com.tokio.cotizadorModular.Bean.SimpleResponse;
import com.tokio.cotizadorModular.Interface.CotizadorGenerico;

import java.io.PrintWriter;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CotizadorPaso3PortletKeys.CotizadorPaso3,
		"mvc.command.name=/cotizadores/validaAgente"
	},
	service = MVCResourceCommand.class
)

public class ValidaAgenteResourceCommand extends BaseMVCResourceCommand {
	
	@Reference
	CotizadorGenerico _CMGenerico;
	
	
	@Override
	protected void doServeResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws Exception {
		
		String auxCodigo = ParamUtil.getString(resourceRequest, "codigoAgente");
		int cotizacion = ParamUtil.getInteger(resourceRequest, "cotizacion");
		String codigoAgente = "";
		
		if(!auxCodigo.isEmpty()) {
			String auxCodAgente[] = auxCodigo.split("-");
			codigoAgente = auxCodAgente[0].trim();
		}
		
		SimpleResponse response = _CMGenerico.validaAgentes(cotizacion, codigoAgente,
				CotizadorPaso3PortletKeys.CotizadorPaso3);
		
		Gson gson = new Gson();
		String jsonString = gson.toJson(response);
		PrintWriter writer = resourceResponse.getWriter();
		writer.write(jsonString);
		
	}

}
