import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import { Button, DatePicker, Dialog, Grid, GridColumn, GridItemModel, TextField, VerticalLayout, ComboBox, GridSortColumn, HorizontalLayout, Select, Icon } from '@vaadin/react-components';
import { Notification } from '@vaadin/react-components/Notification';
import { useSignal } from '@vaadin/hilla-react-signals';
import handleError from 'Frontend/views/_ErrorHandler';
import { Group, ViewToolbar } from 'Frontend/components/ViewToolbar';
import { useDataProvider } from '@vaadin/hilla-react-crud';
import { CancionService } from 'Frontend/generated/endpoints';
import Cancion from 'Frontend/generated/com/unl/music/base/models/Cancion';
import Album from 'Frontend/generated/com/unl/music/base/models/Album';
import Genero from 'Frontend/generated/com/unl/music/base/models/Genero';
import TipoArchivoEnum from 'Frontend/generated/com/unl/music/base/models/TipoArchivoEnum';
import { AlbumService } from 'Frontend/generated/endpoints';
import { GeneroService } from 'Frontend/generated/endpoints';
import { useEffect, useState } from 'react';


export const config: ViewConfig = {
  title: 'Cancion',
  menu: {
    icon: 'vaadin:clipboard-check',
    order: 2,
    title: 'Canciones',
  },
};

type CancionEntryFormProps = {
  onCancionCreated?: () => void;
};

type CancionEntryFormPropsUpdate = {
  onCancionUpdated?: () => void;
};

type CancionEntryFormPropsDelete = {
  onCancionDeleted?: () => void;
};


// Create Cancion
function CancionEntryForm(props: CancionEntryFormProps) {
  const dialogOpened = useSignal(false);
  const [album, setAlbums] = useState<Album[]>([]);
  const [genero, setGeneros] = useState<Genero[]>([]);

  const open = () => {
    dialogOpened.value = true;

  };

  const close = () => {
    dialogOpened.value = false;
  };

  const nombre = useSignal('');
  const id_genero = useSignal(0);
  const duracion = useSignal('');
  const url = useSignal('');
  const tipo = useSignal("");
  const id_album = useSignal(0);

  useEffect(() => {
    const fetchGeneros = async () => {
      const result = await GeneroService.listAllGenero();
      setGeneros(result || []);
    };
    fetchGeneros();
  }, []);

  useEffect(() => {
    const fetchAlbums = async () => {
      const result = await AlbumService.listAll();
      setAlbums(result || []);
    };
    fetchAlbums();
  }, []);

  const createCancion = async () => {
    try {
      if (nombre.value.trim().length > 0 && id_genero.value > 0 && duracion.value > 0 && url.value.trim().length > 0 && tipo.value.trim().length > 0 && id_album.value > 0) {
        const tipoEnum = tipo.value as TipoArchivoEnum;
        await CancionService.createCancion(nombre.value, id_genero.value, duracion.value, url.value, tipo.value, id_album.value);
        if (props.onCancionCreated) {
          props.onCancionCreated();
        }
        nombre.value = '';
        id_genero.value = 0;
        duracion.value = '';
        url.value = '';
        tipo.value = '';
        id_album.value = 0;

        dialogOpened.value = false;
        Notification.show('Cancion creada exitosamente', { duration: 5000, position: 'bottom-end', theme: 'success' });
      } else {
        Notification.show('No se pudo crear, faltan datos', { duration: 5000, position: 'top-center', theme: 'error' });
      }

    } catch (error) {
      console.log(error);
      handleError(error);
    }
  };

  return (
    <>
      <Dialog
        aria-label="Registrar Cancion"
        draggable
        modeless
        opened={dialogOpened.value}
        onOpenedChanged={(event) => {
          dialogOpened.value = event.detail.value;
        }}
        header={
          <h2
            className="draggable"
            style={{
              flex: 1,
              cursor: 'move',
              margin: 0,
              fontSize: '1.5em',
              fontWeight: 'bold',
              padding: 'var(--lumo-space-m) 0',
            }}
          >
            Registrar Cancion
          </h2>
        }
        footerRenderer={() => (
          <>
            <Button onClick={close}>Cancelar</Button>
            <Button theme="primary" onClick={createCancion}>
              Registrar
            </Button>
          </>
        )}
      >
        <VerticalLayout
          theme="spacing"
          style={{ width: '300px', maxWidth: '100%', alignItems: 'stretch' }}
        >
          <VerticalLayout style={{ alignItems: 'stretch' }}>
            <TextField label="Nombre"
              placeholder='Ingrese el nombre de la Cancion'
              aria-label='Ingrese el nombre de la Cancion'
              value={nombre.value}
              onValueChanged={(evt) => (nombre.value = evt.detail.value)}
            />
            <ComboBox
              label="Genero"
              placeholder="Seleccione un genero"
              items={genero}
              itemLabelPath="nombre"
              itemValuePath="id"
              value={id_genero.value}
              onValueChanged={(evt) => (id_genero.value = evt.detail.value)}
            />
            <TextField label="Duracion"
              placeholder='Ingrese la duracion de la cancion'
              aria-label='Ingrese la duracion de la cancion'
              value={duracion.value}
              onValueChanged={(evt) => (duracion.value = evt.detail.value)}
              suffix="minutos"
            />
            <TextField label="Url"
              placeholder='Ingrese la url de la cancion'
              aria-label='Ingrese la url de la cancion'
              value={url.value}
              onValueChanged={(evt) => (url.value = evt.detail.value)}
            />
            <ComboBox
              label="Tipo"
              placeholder="Seleccione el Tipo de archivo"
              items={Object.values(TipoArchivoEnum)}
              itemLabelPath="nombre"
              itemValuePath="id"
              value={tipo.value}
              onValueChanged={(evt) => (tipo.value = evt.detail.value)}
            />
            <ComboBox
              label="Album"
              placeholder="Seleccione el album"
              items={album}
              itemLabelPath="nombre"
              itemValuePath="id"
              value={id_album.value}
              onValueChanged={(evt) => (id_album.value = evt.detail.value)}
            />
          </VerticalLayout>
        </VerticalLayout>
      </Dialog>
      <Button theme="registrar" onClick={open}>
        Registrar
      </Button>
    </>
  );
}


//UPDATE Cancion

function CancionEntryFormUpdate(props: CancionEntryFormPropsUpdate) {
  const Cancion = props.arguments;
  const dialogOpened = useSignal(false);
  const [genero, setGeneros] = useState<Genero[]>([]);
  const [album, setAlbums] = useState<Album[]>([]);

  const open = () => {
    dialogOpened.value = true;
  };

  const close = () => {
    dialogOpened.value = false;
  };

  const ident = props.arguments.id;
  const nombre = useSignal(props.arguments.nombre);
  const id_genero = useSignal(props.arguments.id_genero);
  const duracion = useSignal(props.arguments.duracion);
  const url = useSignal(props.arguments.url);
  const tipo = useSignal(props.arguments.tipo);
  const id_album = useSignal(props.arguments.id_album);

  useEffect(() => {
    const fetchGeneros = async () => {
      const result = await GeneroService.listAllGenero();
      setGeneros(result || []);
    };
    fetchGeneros();
  }, []);

  useEffect(() => {
    const fetchAlbums = async () => {
      const result = await AlbumService.listAll();
      setAlbums(result || []);
    };
    fetchAlbums();
  }, []);

  useEffect(() => {
    nombre.value = props.arguments.nombre;
    id_genero.value = props.arguments.id_genero;
    duracion.value = props.arguments.duracion;
    url.value = props.arguments.url;
    tipo.value = props.arguments.tipo;
    id_album.value = props.arguments.id_album;
  }, [props.arguments]);

  const updateCancion = async () => {
    try {

      if (nombre.value.trim().length > 0 && id_genero.value > 0 && String(duracion.value).trim().length > 0 && url.value.trim().length > 0 &&
        tipo.value.trim().length > 0 && id_album.value > 0) {
        const tipoEnum = tipo.value as TipoArchivoEnum;
        await CancionService.updateCancion(parseInt(ident), nombre.value, id_genero.value, duracion.value, url.value, tipo.value, id_album.value);
        if (props.onCancionUpdated) {
          props.onCancionUpdated();
        }
        nombre.value = '';
        id_genero.value = 0;
        duracion.value = '';
        url.value = '';
        tipo.value = '';
        id_album.value = 0;
        dialogOpened.value = false;
        Notification.show('Cancion modificada exitosamente', { duration: 5000, position: 'bottom-end', theme: 'success' });
      } else {
        Notification.show('No se pudo modificar, faltan datos', { duration: 5000, position: 'top-center', theme: 'error' });
      }
    } catch (error) {
      console.log(error);
      handleError(error);
    }
  };

  return (
    <>
      <Dialog
        aria-label="Editar Cancion"
        draggable
        modeless
        opened={dialogOpened.value}
        onOpenedChanged={(event) => {
          dialogOpened.value = event.detail.value;
        }}
        header={
          <h2
            className="draggable"
            style={{
              flex: 1,
              cursor: 'move',
              margin: 0,
              fontSize: '1.5em',
              fontWeight: 'bold',
              padding: 'var(--lumo-space-m) 0',
            }}
          >
            Editar Cancion
          </h2>
        }
        footerRenderer={() => (
          <>
            <Button onClick={close}>Cancelar</Button>
            <Button theme="primary" onClick={updateCancion}>
              Actualizar
            </Button>
          </>
        )}
      >
        <VerticalLayout
          theme="spacing"
          style={{ width: '300px', maxWidth: '100%', alignItems: 'stretch' }}
        >
          <VerticalLayout style={{ alignItems: 'stretch' }}>
            <TextField label="Nombre"
              placeholder='Ingrese el nombre de la Cancion'
              aria-label='Ingrese el nombre de la Cancion'
              value={nombre.value}
              onValueChanged={(evt) => (nombre.value = evt.detail.value)}
            />
            <ComboBox
              label="Genero"
              placeholder="Seleccione el Genero"
              items={genero}
              itemLabelPath="nombre"
              itemValuePath="id"
              value={id_genero.value}
              onValueChanged={(evt) => (id_genero.value = evt.detail.value)}
            />
            <TextField label="Duracion"
              placeholder='Ingrese la duracion de la Cancion'
              aria-label='Ingrese la duracion de la Cancion'
              value={duracion.value}
              onValueChanged={(evt) => (duracion.value = evt.detail.value)}
              suffix="minutos"
            />
            <TextField label="Url"
              placeholder='Ingrese la URL de la cancion'
              aria-label='Ingrese la URL de la cancion'
              value={url.value}
              onValueChanged={(evt) => (url.value = evt.detail.value)}
            />
            <ComboBox
              label="Tipo"
              placeholder="Seleccione el Tipo de archivo"
              items={Object.values(TipoArchivoEnum)}
              itemLabelPath="nombre"
              itemValuePath="id"
              value={tipo.value}
              onValueChanged={(evt) => (tipo.value = evt.detail.value)}
            />
            <ComboBox
              label="Album"
              placeholder="Seleccione el Album"
              items={album}
              itemLabelPath="nombre"
              itemValuePath="id"
              value={id_album.value}
              onValueChanged={(evt) => (id_album.value = evt.detail.value)}
            />
          </VerticalLayout>
        </VerticalLayout>
      </Dialog>
      <Button theme="editar" onClick={open}>
        Editar
      </Button>
    </>
  );
}
//DELETE Cancion
function CancionEntryFormDelete(props: CancionEntryFormPropsDelete) {
  const dialogOpened = useSignal(false);

  const open = () => {
    dialogOpened.value = true;
  };

  const close = () => {
    dialogOpened.value = false;
  };

  const ident = props.arguments.id;
  const nombre = props.arguments.nombre; 
  const DeleteCancion = async () => {
    try {
      await CancionService.deleteCancion(parseInt(ident));
      if (props.onCancionDeleted) {
        props.onCancionDeleted();
      }
      dialogOpened.value = false;
      Notification.show('Cancion eliminada exitosamente', { duration: 5000, position: 'bottom-end', theme: 'success' });
    } catch (error) {
      console.log(error);
      handleError(error);
    }
  };

  return (
    <>
      <Button theme='eliminar' onClick={open}>Eliminar</Button>
      <Dialog
        aria-label="Eliminar Cancion"
        draggable
        modeless
        opened={dialogOpened.value}
        onOpenedChanged={(event) => {
          dialogOpened.value = event.detail.value;
        }}
        header={
          <h2
            className="draggable"
            style={{
              flex: 1,
              cursor: 'move',
              margin: 0,
              fontSize: '1.5em',
              fontWeight: 'bold',
              padding: 'var(--lumo-space-m) 0',
            }}
          >
            Eliminar Cancion
          </h2>
        }
        footerRenderer={() => (
          <>
            <Button onClick={close}>Cancelar</Button>
            <Button theme="eliminar" onClick={DeleteCancion}>
              Eliminar
            </Button>
          </>
        )}
      >
        <VerticalLayout>
          <span>¿Seguro que deseas eliminar la canción <b>{nombre.value}</b>?</span>
        </VerticalLayout>
      </Dialog>
    </>
  );
}


const dateFormatter = new Intl.DateTimeFormat(undefined, {
  dateStyle: 'medium',
});




function duracionRenderer({ item }: { item: Cancion }) {
  return <span>{item.duracion} minutos</span>;
}


export default function CancionView() {
  const [items, setItems] = useState([]);
  const [originalItems, setOriginalItems] = useState([]);
  const [generos, setGeneros] = useState<Genero[]>([]);
  const [albums, setAlbums] = useState<Album[]>([])
  const [sortState, setSortState] = useState({});

  const callData = () => {
    CancionService.listAll().then(function (data) {
      setItems(data);
      setOriginalItems(data);
    });
  };
  useEffect(() => {
    callData();
    GeneroService.listAllGenero().then(setGeneros);
    AlbumService.listAll().then(setAlbums);
  }, []);

  const criterio = useSignal('');
  const text = useSignal('');
  const itemSelect = [
    { label: 'Nombre', value: 'nombre' },
    { label: 'Genero', value: 'id_genero' },
    { label: 'Url', value: 'url' },
    { label: 'Tipo', value: 'tipo' },
    { label: 'Duracion', value: 'duracion' },
    { label: 'Album', value: 'id_album' },

  ]

  useEffect(() => {
    CancionService.listAll().then(setItems);
    GeneroService.listAllGenero().then(setGeneros);
    AlbumService.listAll().then(setAlbums);
  }, []);

  const order = (event, columnId) => {
    const direction = event.detail.value; 
    setSortState(prev => {
      const newState = { ...prev };
      if (!direction) {
        setItems(originalItems);
        newState[columnId] = null;
      } else {
        var dir = (direction === 'asc' ? 1 : 2);
        CancionService.order(columnId, dir).then(function (data) {
          setItems(data);
        });
        newState[columnId] = direction;
      }
      return newState;
    });
  }

  function generoRenderer({ item }) {
    if (typeof item.id_genero === 'string' && isNaN(Number(item.id_genero))) {
      return <span>{item.id_genero}</span>;
    }
    const genero = generos.find(g => g.id === item.id_genero);
    return <span>{genero ? genero.nombre : 'Sin género'}</span>;
  }

  function albumRenderer({ item }) {
    if (typeof item.id_album === 'string' && isNaN(Number(item.id_album))) {
      return <span>{item.id_album}</span>;
    }
    const album = albums.find(a => a.id === item.id_album);
    return <span>{album ? album.nombre : 'Sin álbum'}</span>;
  }

  function indexLink({ item }: { item, Cancion }) {
    return (
      <span>
        <CancionEntryFormUpdate arguments={item} onCancionUpdated={callData} />
        <CancionEntryFormDelete arguments={item} onCancionDeleted={callData} />

      </span>
    );
  }

  function indexIndex({ model }: { model: GridItemModel<Album> }) {
    return (
      <span>
        {model.index + 1}
      </span>
    )
  }
  const search = async () => {
    try {
      let criterioBusqueda = criterio.value;
      let textoBusqueda = text.value.trim().toLowerCase();

      if (criterioBusqueda === 'id_genero') {
        const ids = generos
          .filter(g => g.nombre.toLowerCase().includes(textoBusqueda))
          .map(g => g.id);
        if (ids.length === 0) {
          setItems([]);
          Notification.show('No se encontró ningún género', { duration: 5000, position: 'bottom-end', theme: 'error' });
          return;
        }

        let allResults: any[] = [];
        for (const id of ids) {

          const data = await CancionService.busqueda('id_genero', id.toString(), 2);
          allResults = allResults.concat(data);
        }
        setItems(allResults);
      } else if (criterioBusqueda === 'id_album') {
        const ids = albums
          .filter(a => a.nombre.toLowerCase().includes(textoBusqueda))
          .map(a => a.id);
        if (ids.length === 0) {
          setItems([]);
          Notification.show('No se encontró ningún álbum', { duration: 5000, position: 'bottom-end', theme: 'error' });
          return;
        }
        let allResults: any[] = [];
        for (const id of ids) {
          const data = await CancionService.busqueda('id_album', id.toString(), 2);
          allResults = allResults.concat(data);
        }
        setItems(allResults);
      } else {
        CancionService.busqueda(criterioBusqueda, text.value, 2).then(function (data) {
          setItems(data);
        });
      }

      text.value = '';

      Notification.show('Busqueda realizada', { duration: 5000, position: 'bottom-end', theme: 'success' });
    } catch (error) {
      console.log(error);
      handleError(error);
    }
  };


  return (
    <main className="w-full h-full flex flex-col box-border gap-s p-m">
      <ViewToolbar title="Canciones">
        <Group>
          <CancionEntryForm onCancionCreated={callData} />
        </Group>
      </ViewToolbar>
      <HorizontalLayout theme="spacing">
        <Select
          items={itemSelect}
          value={criterio.value}
          onValueChanged={(e) => (criterio.value = e.detail.value)}
          label="Criterio de busqueda"
        />
        <TextField
          placeholder="Search"
          style={{ width: '50%' }}
          value={text.value}
          onValueChanged={(evt) => (text.value = evt.detail.value)}
        >
          <Icon slot="prefix" icon="vaadin:search" />
        </TextField>
        <Button onClick={search} theme="primary">
          BUSCAR
        </Button>
        <Button onClick={callData} theme="secondary">
          Ver toda la lista
        </Button>

      </HorizontalLayout>
      <Grid items={items}>
        <GridColumn renderer={indexIndex} header="Nro " />
        <GridSortColumn path="nombre" header="Nombre" onDirectionChanged={(e) => order(e, 'nombre')} />
        <GridSortColumn path="id_genero" header="Genero" renderer={generoRenderer} onDirectionChanged={(e) => order(e, 'id_genero')} />
        <GridSortColumn path="duracion" header="Duracion" renderer={duracionRenderer} onDirectionChanged={(e) => order(e, 'duracion')} />
        <GridSortColumn path="url" header="Url" onDirectionChanged={(e) => order(e, 'url')} />
        <GridSortColumn path="tipo" header="Tipo" onDirectionChanged={(e) => order(e, 'tipo')} />
        <GridSortColumn path="id_album" header="Album" renderer={albumRenderer} onDirectionChanged={(e) => order(e, 'id_album')} />
        <GridColumn renderer={indexLink} header="Acciones" />
      </Grid>
    </main>
  );
}
