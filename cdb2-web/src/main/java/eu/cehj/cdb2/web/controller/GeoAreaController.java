package eu.cehj.cdb2.web.controller;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.security.access.annotation.Secured;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.querydsl.core.types.Predicate;

import eu.cehj.cdb2.business.service.db.GeoAreaService;
import eu.cehj.cdb2.common.dto.GeoAreaDTO;
import eu.cehj.cdb2.common.dto.GeoAreaSimpleDTO;
import eu.cehj.cdb2.entity.GeoArea;

@RestController
@RequestMapping("api/geo-area")
public class GeoAreaController extends BaseController {

	@Autowired
	GeoAreaService areaService;

	@RequestMapping(method = { GET })
	@ResponseStatus(value = OK)
	@Secured(value = { "ROLE_VIEWER", "ROLE_USER", "ROLE_SUPER_USER", "ROLE_ADMIN"})
	public List<GeoAreaDTO> get() {
		return this.areaService.getAllDTO();
	}

	@RequestMapping(method = { GET }, value = "/simple")
	@ResponseStatus(value = OK)
	@Secured(value = { "ROLE_VIEWER", "ROLE_USER", "ROLE_SUPER_USER", "ROLE_ADMIN"})
	public List<GeoAreaSimpleDTO> getSimple(){
		return this.areaService.getAllSimpleDTO();
	}

	@RequestMapping(method = { POST })
	@ResponseStatus(value = OK)
	@Secured(value = { "ROLE_USER", "ROLE_SUPER_USER", "ROLE_ADMIN"})
	public GeoAreaDTO save(@RequestBody final GeoAreaDTO dto){
		return this.areaService.saveDTO(dto);
	}

	@RequestMapping( method = DELETE, value = "/{id}" )
	@ResponseStatus(value = NO_CONTENT)
	@Secured(value = {"ROLE_USER", "ROLE_SUPER_USER", "ROLE_ADMIN"})
	public void delete(final Model model, @PathVariable(value = "id") final Long id) {
		this.areaService.delete(id);
	}

	@RequestMapping(method = RequestMethod.GET, value="search")
	@ResponseStatus(value = OK)
	@Secured(value = { "ROLE_VIEWER", "ROLE_USER", "ROLE_SUPER_USER", "ROLE_ADMIN"})
	public Page<GeoAreaDTO> search(
			@QuerydslPredicate(root = GeoArea.class) final Predicate predicate, final Pageable pageable){
		return this.areaService.findAll(predicate, pageable);
	}
}