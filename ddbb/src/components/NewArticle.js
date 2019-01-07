import React from 'react';
import ReactQuill from 'react-quill'; // ES6
import 'react-quill/dist/quill.snow.css'; // ES6
import Select, { Option } from 'rc-select';
require ('rc-select/assets/index.css');
require('../styles/new-article.css');
require('../styles/github-markdown.css');
import ReactCrop from 'react-image-crop';
require ('react-image-crop/dist/ReactCrop.css');
import Rodal from 'rodal';
import 'rodal/lib/rodal.css';
import hljs from 'highlight.js'

import axios from 'axios'
import qs from 'qs'

export default class NewArticle extends React.Component {
  static defaultProps = {
    modules: {
      toolbar: {
        container: "#toolbar",
        handlers: {
          "insertImg": insertImg
        }
      }
    }
  }
  constructor(props) {
    super(props);
    this.state = {
      title:'',
      descr:'',
      cover: '',
      visible: false,
      imgSrc: '',
      crop: {
        x: 5,
        y: 5,
        width: 90,
        height: 90,
        aspect: 2.2/1
      },
      pixelCrop: {},
      file: null,
      category: '',
      tags: [],
      text: ``,
      categoryOptions: [],
      tagsOptions: []
    } // You can also pass a Quill Delta here
    this.handleChange = this.handleChange.bind(this)
  }
  componentWillMount() {
    this.getTagOptions();
    this.getCategoryOptions();
  }
  getTagOptions = () => {
    axios.get('/api/tags').then( res => {
      let tagsOptions = res.data.data;
      this.setState({tagsOptions});
    })
  }
  getCategoryOptions = () => {
    axios.get('/api/category').then( res => {
      let categoryOptions = res.data.data;
      this.setState({categoryOptions});
    })
  }
  onCategoryChange = (value) => {
    this.setState({
      category: value
    });
  }
  onTagsChange = (value) => {
    this.setState({
      tags: value
    });
    let existed = false;
    this.state.tagsOptions.map(tagObject => {
      if (tagObject.tagName === value[value.length-1]) {
        existed = true;
      }
    })
    if (!existed && value[value.length-1]) {
      axios.post('api/tags', qs.stringify({tagName: value[value.length-1]})).then(() => {
        this.getTagOptions();
      })
    }
  }
  toggleDisabled = () => {
    this.setState({
      disabled: !this.state.disabled
    });
  }
  handleChange(value) {
    this.setState({ text: value })
  }
  dataURLtoBlob = (dataurl) => {
      var arr = dataurl.split(','), mime = arr[0].match(/:(.*?);/)[1],
          bstr = atob(arr[1]), n = bstr.length, u8arr = new Uint8Array(n);
      while(n--){
          u8arr[n] = bstr.charCodeAt(n);
      }
      return new Blob([u8arr], {type:mime});
  }
  handleTitleChange = (e) => {
    this.setState({title: e.target.value})
  }
  handleDescrChange = (e) => {
    this.setState({descr: e.target.value})
  }
  handleCoverChange = (e) => {
    // 坑
    this.setState({cover: e.target.value})
  }
  getCategoryId = (categoryName) => {
    let categoryId = 0;
    this.state.categoryOptions.map(category => {
      if (category.categoryName === categoryName) {
        categoryId = category.categoryId;
      }
    })
    return categoryId;
  }
  getTagString = () => {
    let tagIds = [];
    this.state.tags.map(tagName => {
      this.state.tagsOptions.map(tagItem => {
        if (tagItem.tagName === tagName) {
          tagIds.push(tagItem.tagId)
        }
      })
    })
    return tagIds.join(',');
  }
  onCropComplete = (crop) => {
    let naturalWidth = document.getElementsByClassName('ReactCrop__image')[0].naturalWidth;
    let naturalHeight = document.getElementsByClassName('ReactCrop__image')[0].naturalHeight;
    let pixelCrop = {};
    pixelCrop.x = Math.floor(naturalWidth * Math.floor(crop.x) / 100);
    pixelCrop.y = Math.floor(naturalHeight * Math.floor(crop.y) / 100);
    pixelCrop.width = Math.floor(naturalWidth * Math.floor(crop.width) / 100);
    pixelCrop.height = Math.floor(naturalHeight * Math.floor(crop.height) / 100);
    this.setState({ crop, pixelCrop});
  }
  show = () => {
      this.setState({ visible: true });
  }

  hide = () =>  {
      this.setState({ visible: false });
  }
  selectFile = () => {
    let __this = this;
    let fileInput = document.querySelector('input.cover-image[type=file]');
    if (fileInput == null) {
      fileInput = document.createElement('input');
      fileInput.setAttribute('type', 'file');
      fileInput.setAttribute('accept', 'image/png, image/gif, image/jpeg, image/bmp, image/x-icon');
      fileInput.classList.add('cover-image');
      fileInput.addEventListener('change', () => {
        if (fileInput.files != null && fileInput.files[0] != null) {
          __this.setState({file: fileInput.files[0]})
          const reader = new FileReader();
          reader.readAsDataURL(fileInput.files[0]);
          reader.onload = (event) => {
            __this.setState({imgSrc: event.target.result}, function(){
              __this.setState({visible: true})
            })

          };
        }
      });
    }
    fileInput.click();
  }
  uploadCover = () => {
    let file = this.state.file;
    let formdata = new FormData();
    formdata.append("file", file);
    formdata.append("x", this.state.pixelCrop.x);
    formdata.append("y", this.state.pixelCrop.y);
    formdata.append("w", this.state.pixelCrop.width);
    formdata.append("h", this.state.pixelCrop.height);
    this.setState({ visible: false });
    axios({
      url:'/api/uploadCover',
      method:'post',
      data:formdata,
      headers: {'Content-Type': 'application/x-www-form-urlencoded'}
    }).then(res => {
      this.setState({cover: res.data.data})
    })
  }
  submit = () => {
    hljs.configure({
      languages: ['java','javascript', 'c', 'c++', 'html', 'xml', 'css', 'json']
    })
    let blocks = document.getElementsByTagName('pre');
    for(let i = 0; i < blocks.length; i++){
      hljs.highlightBlock(blocks[i]);
    }
    let title = this.state.title;
    let cover = this.state.cover;
    let descr = this.state.descr;
    let text = document.getElementsByClassName('ql-editor')[0].innerHTML;
    let categoryId = this.getCategoryId(this.state.category);
    let tagString = this.getTagString();

    axios.post('/api/article', qs.stringify({title: title, descr:descr, content: text, categoryId, tagString, cover})).then(() => {
      this.props.history.push('/article');
    });
  }
  render() {
    let tagsOptions = this.state.tagsOptions.map( (tag) => (
      <Option key={tag.tagName}>{tag.tagName}</Option>
    ))
    let categoryOptions = this.state.categoryOptions.map( (category) => (
      <Option key={category.categoryName}>{category.categoryName}</Option>
    ))
    return (
      <div className="new-article">
        <div className="main-content-wrap">
          <div className="form-item">文章标题：
            <input type="text" className="title" value={this.state.title} onChange={this.handleTitleChange}/>
          </div>
          <div className="form-item">文章摘要：
            <textarea style={{height: 200}} className="title" value={this.state.descr} onChange={this.handleDescrChange}>
            </textarea>
          </div>
          <div className="form-item">封面图地址：
            <div className="btn-group margin0 marginLeft10">
              <button className="btn btn-mini is-plain" onClick={this.selectFile}>本地上传</button>
              {
                this.state.imgSrc ? <button className="btn btn-mini is-plain marginLeft10" onClick={this.show.bind(this)}>重新编辑</button> : ''
              }
             </div>
            <input type="cover" className="title" value={this.state.cover} onChange={this.handleCoverChange}/>
          </div>
          {this.state.cover ?<div>预览：<br/> <img className="preview" src={this.state.cover} alt=""/></div> : ''}
          <div className="crop">
            <Rodal visible={this.state.visible} style={{paddingTop: '40px'}} onClose={this.hide.bind(this)}>
              <span style={{lineHeight: '200%', fontSize: '20px'}}>图片裁剪：</span>
              <div className='corp-box'>
                <ReactCrop src={this.state.imgSrc} crop={this.state.crop}
                  onComplete={this.onCropComplete} />
              </div>
              <div className="model-footer">
                <button className="btn" onClick={this.hide.bind(this)}>
                    取消
                </button>
                <button className="btn btn-primary" onClick={this.uploadCover.bind(this)}>
                    确定
                </button>
              </div>
            </Rodal>
          </div>
          <div className="form-item">所属分类：
            <Select
              className="category-select"
              allowClear
              value={this.state.category}
              defaultValue="lucy"
              animation="slide-up"
              showSearch={true}
              onChange={this.onCategoryChange}
            >
              {categoryOptions}
            </Select>
          </div>
          <div className="form-item">标签：
            <Select
              tags
              dropdownMenuStyle={{ maxHeight: 200, overflow: 'auto' }}
              style={{ width: '100%' }}
              maxTagTextLength={10}
              animation="slide-up"
              value={this.state.tags}
              onChange={this.onTagsChange}
              tokenSeparators={[' ', ',']}
            >
              {tagsOptions}
            </Select>
          </div>
        </div>
        <div className="main-content-wrap">
          <CustomToolbar />
          <ReactQuill
            onChange={this.handleChange}
            placeholder={this.props.placeholder}
            modules={this.props.modules}
            theme={"snow"} // pass false to use minimal theme
            value={this.state.text}
          >
            <div
              key="editor"
              ref="editor"
              className="quill-contents"
            />
          </ReactQuill>
        </div>

        <div className="main-content-wrap">
          <div style={{marginBottom: '10px'}} onClick={this.submit} className="btn btn-primary floatRight">发布博客</div>
        </div>
      </div>
    );
  }
}

const CustomToolbar = () => (
  <div id="toolbar">
    <select className="ql-header">
      <option value="2"></option>
      <option value="3"></option>
      <option value="4"></option>
      <option selected></option>
    </select>
    <select className="ql-align">
      <option selected="selected"></option>
      <option value="center"></option>
      <option value="right"></option>
      <option value="justify"></option>
    </select>
    <button className="ql-bold"></button>
    <button className="ql-italic"></button>
    <button className="ql-list" value="ordered">
      <svg viewBox="0 0 18 18"> <line className="ql-stroke" x1="6" x2="15" y1="4" y2="4"></line> <line className="ql-stroke" x1="6" x2="15" y1="9" y2="9"></line> <line className="ql-stroke" x1="6" x2="15" y1="14" y2="14"></line> <line className="ql-stroke" x1="3" x2="3" y1="4" y2="4"></line> <line className="ql-stroke" x1="3" x2="3" y1="9" y2="9"></line> <line className="ql-stroke" x1="3" x2="3" y1="14" y2="14"></line> </svg>
    </button>
    <button className="ql-list" value="bullet">
      <svg viewBox="0 0 18 18"> <line className="ql-stroke" x1="6" x2="15" y1="4" y2="4"></line> <line className="ql-stroke" x1="6" x2="15" y1="9" y2="9"></line> <line className="ql-stroke" x1="6" x2="15" y1="14" y2="14"></line> <line className="ql-stroke" x1="3" x2="3" y1="4" y2="4"></line> <line className="ql-stroke" x1="3" x2="3" y1="9" y2="9"></line> <line className="ql-stroke" x1="3" x2="3" y1="14" y2="14"></line> </svg>
    </button>
    <button type="button" className="ql-code-block"><svg viewBox="0 0 18 18"> <polyline className="ql-even ql-stroke" points="5 7 3 9 5 11"></polyline> <polyline className="ql-even ql-stroke" points="13 7 15 9 13 11"></polyline> <line className="ql-stroke" x1="10" x2="8" y1="5" y2="13"></line> </svg>
    </button>
    <button type="button" className="ql-blockquote"><svg viewBox="0 0 18 18"> <rect className="ql-fill ql-stroke" height="3" width="3" x="4" y="5"></rect> <rect className="ql-fill ql-stroke" height="3" width="3" x="11" y="5"></rect> <path className="ql-even ql-fill ql-stroke" d="M7,8c0,4.031-3,5-3,5"></path> <path className="ql-even ql-fill ql-stroke" d="M14,8c0,4.031-3,5-3,5"></path> </svg>
    </button>
    <button type="button" className="ql-link"></button>
    <button className="ql-insertImg">
      <svg viewBox="0 0 18 18"> <rect className="ql-stroke" height="10" width="12" x="3" y="4"></rect> <circle className="ql-fill" cx="6" cy="7" r="1"></circle> <polyline className="ql-even ql-fill" points="5 12 5 11 7 9 8 10 11 7 13 9 13 12 5 12"></polyline> </svg>
    </button>

    {/*code-inline*/}
    <button className="ql-clean"></button>
  </div>
)
function insertImg () {
  let __this = this;
  let fileInput = this.container.querySelector('input.ql-image[type=file]');
  if (fileInput == null) {
    fileInput = document.createElement('input');
    fileInput.setAttribute('type', 'file');
    fileInput.setAttribute('accept', 'image/png, image/gif, image/jpeg, image/bmp, image/x-icon');
    fileInput.classList.add('ql-image');
    fileInput.addEventListener('change', () => {
      if (fileInput.files != null && fileInput.files[0] != null) {
        var formdata = new FormData();
        formdata.append("file", fileInput.files[0]);
        axios({
            url:'/api/uploadArticlePic',
            method:'post',
            data:formdata,
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        }).then(res => {
          __this.quill.insertEmbed(__this.quill.getSelection().index, 'image', res.data.data);
          __this.quill.setSelection(__this.quill.getSelection().index + 1);
          fileInput.value = "";
        })
      }
    });
  }
  fileInput.click();
}
