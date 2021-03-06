/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package domainapp.modules.simple.dom.tarea;

import domainapp.modules.simple.dom.mantenimiento.Mantenimiento;
import domainapp.modules.simple.dom.mantenimiento.MantenimientoRepositorio;
import domainapp.modules.simple.dom.planta.Planta;
import domainapp.modules.simple.dom.planta.PlantaRepositorio;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.datanucleus.query.typesafe.TypesafeQuery;

import java.util.List;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        objectType = "simple.TareaMenu",
        repositoryFor = Tarea.class
)
@DomainServiceLayout(
        named = "Tareas",
        menuOrder = "10"
)
public class TareaRepositorio {

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named="Listar Tareas: ")
    @MemberOrder(sequence = "1")
    public List<Tarea> listAll() {
        return repositoryService.allInstances(Tarea.class);
    }

    public static class CreateDomainEvent extends ActionDomainEvent<TareaRepositorio> {}
    @Action(domainEvent = CreateDomainEvent.class)
    @MemberOrder(sequence = "2")
    public Tarea create(
            final @ParameterLayout(named="Nombre") String nombre,
            final @ParameterLayout(named="Detalle") String detalle,

            final @Parameter(optionality = Optionality.MANDATORY)
            @ParameterLayout(named="Mantenimiento") Mantenimiento mantenimiento

            ) {
        return repositoryService.persist(new Tarea(nombre, detalle, mantenimiento));
    }

    // ESTO ES PARA EL DROPDOWNLIST
    public List<Mantenimiento> choices2Create() {return mantenimientoRepositorio.listAll();}

    @javax.inject.Inject
    RepositoryService repositoryService;

    @javax.inject.Inject
    IsisJdoSupport isisJdoSupport;

    @javax.inject.Inject
    MantenimientoRepositorio mantenimientoRepositorio;

}
