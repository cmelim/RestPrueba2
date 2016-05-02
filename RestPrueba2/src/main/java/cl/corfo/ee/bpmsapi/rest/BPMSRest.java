package cl.corfo.ee.bpmsapi.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import cl.corfo.ee.bpmsapi.vo.BpmsVO;

@Path("/")
@Produces({MediaType.APPLICATION_JSON})
public interface BPMSRest {
	
	@POST
	@Path("/1.0/startProcess")
	@Consumes({MediaType.APPLICATION_JSON})
	public Response startProcess(BpmsVO bpmsVO);
	
	@POST
	@Path("/1.0/completeTask")
	@Consumes({MediaType.APPLICATION_JSON})
	public Response completeTask(BpmsVO bpmsVO);
	
	@POST
	@Path("/1.0/claimTask")
	@Consumes({MediaType.APPLICATION_JSON})
	public Response claimTask(BpmsVO bpmsVO);
	
	@POST
	@Path("/1.0/endTask")
	@Consumes({MediaType.APPLICATION_JSON})
	public Response endTask(BpmsVO bpmsVO);
	
	@POST
	@Path("/1.0/retrieve/list/task/bypotentialowner")
	@Consumes({MediaType.APPLICATION_JSON})
	public Response listTasksByPotentialOwner(BpmsVO bpmsVO);
	
	@POST
	@Path("/1.0/retrieve/list/task/bycontribuyente")
	@Consumes({MediaType.APPLICATION_JSON})
	public Response listTasksByContribuyente(BpmsVO bpmsVO);
	
	@POST
	@Path("/1.0/retrieve/variable/byprocess")
	@Consumes({MediaType.APPLICATION_JSON})
	public Response getVariableFromProcess(BpmsVO bpmsVO);
	
	@POST
	@Path("/1.0/retrieve/list/process/all")
	@Consumes({MediaType.APPLICATION_JSON})
	public Response getAllProcess(BpmsVO bpmsVO);
	
	@POST
	@Path("/1.0/retrieve/list/process/bycontribuyente")
	@Consumes({MediaType.APPLICATION_JSON})
	public Response getProcessByContribuyente(BpmsVO bpmsVO);

}
