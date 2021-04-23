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
package domainapp.modules.simple.dom.planta;

import com.google.common.collect.ComparisonChain;
import domainapp.modules.simple.dom.equipo.Equipo;
import domainapp.modules.simple.dom.impl.SimpleObject;
import lombok.AccessLevel;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.services.title.TitleService;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.VersionStrategy;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import static org.apache.isis.applib.annotation.CommandReification.ENABLED;
import static org.apache.isis.applib.annotation.SemanticsOf.IDEMPOTENT;

@javax.jdo.annotations.PersistenceCapable(identityType=IdentityType.DATASTORE, schema = "simple")
@javax.jdo.annotations.DatastoreIdentity(strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY, column="id")
@javax.jdo.annotations.Version(strategy= VersionStrategy.DATE_TIME, column="version")
@javax.jdo.annotations.Unique(name="Planta_nombre_UNQ", members = {"nombre"})
@DomainObject(auditing = Auditing.ENABLED)
@DomainObjectLayout()  // causes UI events to be triggered
@lombok.Getter @lombok.Setter
@lombok.RequiredArgsConstructor
public class Planta implements Comparable<Planta> {

    @Column(allowsNull = "false", length = 40)
    @lombok.NonNull
    @Property()
    @Title(prepend = "Planta: ")
    @MemberOrder(sequence = "1")
    private String nombre;

    @Column(allowsNull = "false", length = 40)
    @lombok.NonNull
    @Property()
    @MemberOrder(sequence = "2")
    private String provincia;

    @Column(allowsNull = "false", length = 40)
    @lombok.NonNull
    @Property()
    @MemberOrder(sequence = "3")
    private String cliente;

    @Column(allowsNull = "true")
    @lombok.NonNull
    @MemberOrder(sequence = "4")
    @Persistent(mappedBy="planta", dependentElement="true", defaultFetchGroup = "true")
    @CollectionLayout(named="Equipos")
    private SortedSet<Equipo> listaEquipos = new TreeSet<Equipo>();


    @Override
    public String toString() {
        return getNombre();
    }

    public int compareTo(final Planta other) {
        return ComparisonChain.start()
                .compare(this.getNombre(), other.getNombre())
                .result();
    }

    // ACTUALIZAR PLANTA
    @Action()
    @ActionLayout(named = "Actualizar")
    public Planta update(
            @Parameter(maxLength = 40) @ParameterLayout(named = "Nombre: ") final String nombre,
            @Parameter(maxLength = 40) @ParameterLayout(named = "Provincia: ") final String provincia,
            @Parameter(maxLength = 40) @ParameterLayout(named = "Cliente: ") final String cliente
            ){
        this.nombre=nombre;
        this.provincia=provincia;
        this.cliente=cliente;
        return this;
    }

    public String default0Update() {return getNombre();}

    public String default1Update() {return getProvincia();}

    public String default2Update() {return getCliente();}


    @javax.inject.Inject
    @javax.jdo.annotations.NotPersistent
    @lombok.Getter(AccessLevel.NONE) @lombok.Setter(AccessLevel.NONE)
    RepositoryService repositoryService;

    @javax.inject.Inject
    @javax.jdo.annotations.NotPersistent
    @lombok.Getter(AccessLevel.NONE) @lombok.Setter(AccessLevel.NONE)
    TitleService titleService;

    @javax.inject.Inject
    @javax.jdo.annotations.NotPersistent
    @lombok.Getter(AccessLevel.NONE) @lombok.Setter(AccessLevel.NONE)
    MessageService messageService;

}