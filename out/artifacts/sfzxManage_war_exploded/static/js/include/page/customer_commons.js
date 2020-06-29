"use strict";
var _typeof =
    "function" == typeof Symbol && "symbol" == typeof Symbol.iterator
      ? function (e) {
          return typeof e;
        }
      : function (e) {
          return e &&
            "function" == typeof Symbol &&
            e.constructor === Symbol &&
            e !== Symbol.prototype
            ? "symbol"
            : typeof e;
        },
  config = {
    isKhGh: yo.getQueryString("isKhGh") || 0,
    role: localStorage.userRole,
    power: localStorage.power,
    role_only: localStorage.role_only,
    api_showSaleCustomer: "/saleCustomer/showCustomerKhgh",
    api_qdKhgh: "/saleCustomer/qdKhgh",
    api_import: "/saleCustomer/importExcel.do",
    api_moveKh: "/saleCustomer/moveKh.do",
    api_del: "/saleCustomer/delete.do",
    api_isGh: "/saleCustomer/isGh",
  };
require(["dateTimeInit"]),
  require(["select2"], function (e) {
    window.app = new Vue({
      el: "#app",
      data: {
        c: { list: [] },
        role: config.role,
        power: config.power,
        role_only: config.role_only,
        isKhGh: config.isKhGh,
        postData: {},
        checkAll: 0,
        checks: [],
        posting: !1,
        s: { lastSaleName: {}, provinceName: "", cityName: "", areaName: "" },
        isCream: "商务顾问J" == config.role || "经理" == config.role,
        select2: { privince: {}, city: {}, saleId: {}, area: {} },
        citys_sub: [],
        citys_sub1: [],
        isGh: 1,
        isActive1: !1,
        isActive2: !1,
        isActive3: !1,
        isActive4: !1,
        isActive5: !1,
        isActive6: !1,
        yyxcy: !1,
        yyx: !0,
        wyf: !0,
        wyf1: !1,
        yycy: !1,
        yycy1: !0,
      },
      watch: {
        "s.provinceName": {
          handler: function () {
            var t = this.s.provinceName,
              i = [],
              c = void 0;
            console.log(t),
              $.each(this.citys["省份"], function (e, a) {
                t == a.regionName && (c = a.id);
              }),
              $.each(this.citys["市区"], function (e, a) {
                c == a.parentId && i.push(a);
              }),
              (this.citys_sub = i),
              Vue.set(this.citys_sub);
          },
          deep: !0,
        },
        "s.cityName": {
          handler: function () {
            var t = this.s.provinceName,
              i = this.s.cityName,
              c = [],
              s = void 0,
              l = void 0;
            console.log(i),
              $.each(this.citys["省份"], function (e, a) {
                t == a.regionName && (s = a.id);
              }),
              $.each(this.citys["市区"], function (e, a) {
                s == a.parentId &&
                  i == a.regionName &&
                  ((l = a.id), console.log(l));
              }),
              $.each(this.citys["区"], function (e, a) {
                l == a.parentId && c.push(a);
              }),
              (this.citys_sub1 = c),
              Vue.set(this.citys_sub1);
          },
          deep: !0,
        },
      },
      computed: {},
      created: function () {
        var i = this;
        document.getElementById("app").classList.remove("hide"),
          localStorage.citys && (this.citys = JSON.parse(localStorage.citys)),
          "高级商务顾问S2" == config.role ||
          "高级商务顾问S3" == config.role ||
          "经理" == config.role ||
          "客服" == config.role
            ? ((i.isKhGh = 7), (i.isActive1 = !0))
            : "高级商务顾问S1" == config.role
            ? ((i.isKhGh = 6), (i.isActive2 = !0))
            : "商务顾问J" == config.role || "商务助理" == config.role
            ? ((i.isKhGh = 4), (i.isActive3 = !0))
            : "电销主管" == config.role
            ? ((i.isKhGh = 5),
              (i.isActive4 = !0),
              (i.yyxcy = !0),
              (i.yycy1 = !1),
              (i.yycy = !0),
              (i.wyf = !1),
              (i.wyf1 = !0),
              $("#excel_fileUp").hide())
            : "电销商务顾问" == config.role
            ? ((i.isKhGh = 5),
              (i.isActive4 = !0),
              (i.yyxcy = !0),
              (i.yycy1 = !1),
              (i.yycy = !0),
              (i.wyf = !1),
              (i.wyf1 = !0),
              $("#excel_fileUp").hide())
            : "电销员工" == config.role
            ? ((i.isKhGh = 0), (i.isActive6 = !0))
            : 1 == i.isGh && ((i.isKhGh = 3), (i.isActive5 = !0), (i.yyx = !1)),
          require(["webuploader"], function (e) {
            (i.uploader = e.create({
              server: config.api_import,
              formData: { isKhgh: i.isKhGh },
              timeout: 0,
              runtimeOrder: "html5",
              pick: { id: "#excel_fileUp", innerHTML: "导入客户" },
              resize: !1,
              fileNumLimit: 10,
            })),
              i.uploader.on("uploadSuccess", function (e, a) {
                if ((i.uploader.reset(), i.loading("close"), a.result))
                  var t = layer.alert(
                    "上传成功<br>以下项目有重复无法导入:<br>" + a.result,
                    function () {
                      i.list_Get(1), layer.close(t);
                    }
                  );
                else
                  layer.msg("上传成功"),
                    setTimeout(function () {
                      i.list_Get(1);
                    }, 2e3);
              }),
              i.uploader.on("uploadStart", function () {
                i.loading();
              }),
              i.uploader.on("uploadError", function (e) {
                layer.msg("上传出错");
              }),
              i.uploader.on("error", function (e) {
                "Q_TYPE_DENIED" == e && layer.msg("上传文件格式错误，请检查");
              }),
              i.uploader.on("uploadFinished", function (e) {}),
              i.uploader.on("fileQueued", function () {
                i.uploader.upload();
              });
          });
      },
      mounted: function () {
        this.$watch("checks", function (e, a) {
          t.checks.length == t.c.list.length && 0 != t.checks.length
            ? (t.checkAll = 1)
            : (t.checkAll = 0);
        }),
          (this.s.lastSaleName = this.select_init(
            "[name=saleName]",
            "请选择前负责人",
            1
          ));
        var t = this;
        (this.select2.province = $("select[name=provinceName]")
          .select2({ language: "zh-CN", placeholder: "请选择", allowClear: 1 })
          .on("change", function () {
            (t.s.provinceName = this.value),
              t.select2.city.val(null).trigger("change");
          })),
          (t.select2.city = $("select[name=cityName]")
            .select2({
              language: "zh-CN",
              placeholder: "请选择",
              allowClear: 1,
            })
            .on("change", function () {
              (t.s.cityName = this.value),
                t.select2.area.val(null).trigger("change");
            })),
          (t.select2.area = $("select[name=area]")
            .select2({
              language: "zh-CN",
              placeholder: "请选择",
              allowClear: 1,
            })
            .on("change", function () {
              t.s.areaName = this.value;
            })),
          this.sf(),
          this.list_Get(1),
          "经理" == config.role ||
          "高级商务顾问S2" == config.role ||
          "高级商务顾问S3" == config.role ||
          "高级商务顾问S1" == config.role ||
          "商务顾问J" == config.role
            ? (this.s.lastSaleName = this.select_init(
                "[name=saleName]",
                "请选择前负责人",
                1,
                "e350acd05a6244c79136616302b7dfd6,b729e9334ad64c15a4e47d88b8c2638f,008615f04f39499fb73e5f323add988a,6f247fed28e94194ac66020e55957496,28f1842e2463492a9eb6ddf0def91658,73340766cf3d43af91daff3128adad84,b62d6d19bed64ee199f2c1a5b252b57b"
              ))
            : "电销主管" == config.role
            ? (this.s.lastSaleName = this.select_init(
                "[name=saleName]",
                "请选择电销员工",
                1,
                "7d2f882f13ea48e0a4c8acaeea53b3a5,30b1487221464d369ca4c2462ccca920,01dc6d29f1704efeab0376d327f47d98"
              ))
            : "电销员工" == config.role
            ? (this.s.lastSaleName = this.select_init(
                "[name=saleName]",
                "请选择前负责人",
                1,
                "7d2f882f13ea48e0a4c8acaeea53b3a5,30b1487221464d369ca4c2462ccca920,01dc6d29f1704efeab0376d327f47d98,e350acd05a6244c79136616302b7dfd6,b729e9334ad64c15a4e47d88b8c2638f,008615f04f39499fb73e5f323add988a,6f247fed28e94194ac66020e55957496,28f1842e2463492a9eb6ddf0def91658,73340766cf3d43af91daff3128adad84,b62d6d19bed64ee199f2c1a5b252b57b"
              ))
            : "电销商务顾问" == config.role
            ? (this.s.lastSaleName = this.select_init(
                "[name=saleName]",
                "请选择电销员工",
                1,
                "7d2f882f13ea48e0a4c8acaeea53b3a5,30b1487221464d369ca4c2462ccca920,01dc6d29f1704efeab0376d327f47d98"
              ))
            : (this.s.lastSaleName = this.select_init(
                "[name=saleName]",
                "请选择前负责人",
                1,
                "7d2f882f13ea48e0a4c8acaeea53b3a5,30b1487221464d369ca4c2462ccca920,01dc6d29f1704efeab0376d327f47d98"
              ));
      },
      methods: {
        clear: function () {
          this.s.lastSaleName.val(null).trigger("change"),
            this.select2.province.val(null).trigger("change"),
            this.select2.city.val(null).trigger("change"),
            this.select2.area.val(null).trigger("change"),
            (this.postData.createTimeStart = ""),
            (this.postData.createTimeEnd = "");
        },
        btn_checkAll: function () {
          var e = this;
          (e.checks = []),
            e.checkAll &&
              $.each(e.c.list, function () {
                e.checks.push(this.id);
              });
        },
        shiftToOther: function () {
          var a = this;
          if (this.checks.length) {
            a.select2.saleId = a.select_init(
              "#salerId",
              "请选择前负责人",
              1,
              "7d2f882f13ea48e0a4c8acaeea53b3a5,30b1487221464d369ca4c2462ccca920,01dc6d29f1704efeab0376d327f47d98,90564dd8b75a4f6d815ce418462d401c,e350acd05a6244c79136616302b7dfd6,b729e9334ad64c15a4e47d88b8c2638f,008615f04f39499fb73e5f323add988a,6f247fed28e94194ac66020e55957496,28f1842e2463492a9eb6ddf0def91658,73340766cf3d43af91daff3128adad84,b62d6d19bed64ee199f2c1a5b252b57b"
            );
            var t = layer.open({
              type: 1,
              title: "转移客户",
              closeBtn: 1,
              content: $("#salerBox"),
              area: ["350px", "200px"],
              btn: "转移",
              btnAlign: "c",
              end: function () {
                a.select2.saleId.val(null),
                  (a.select2.saleId = a.select_init(
                    "#salerId",
                    "请选择前负责人",
                    !1,
                    "7d2f882f13ea48e0a4c8acaeea53b3a5,30b1487221464d369ca4c2462ccca920,01dc6d29f1704efeab0376d327f47d98,90564dd8b75a4f6d815ce418462d401c,e350acd05a6244c79136616302b7dfd6,b729e9334ad64c15a4e47d88b8c2638f,008615f04f39499fb73e5f323add988a,6f247fed28e94194ac66020e55957496,28f1842e2463492a9eb6ddf0def91658,73340766cf3d43af91daff3128adad84,b62d6d19bed64ee199f2c1a5b252b57b"
                  ));
              },
              yes: function () {
                if ($("#salerId").val()) {
                  var e = "";
                  0 == a.isKhGh
                    ? (e = 0)
                    : 3 == a.isKhGh
                    ? (e = 1)
                    : 1 == a.isKhGh
                    ? (e = 3)
                    : 4 == a.isKhGh && (e = 4),
                    yocto.json(
                      config.api_moveKh,
                      { id: a.checks, saleId: $("#salerId").val(), type: e },
                      function (e) {
                        layer.close(t), layer.msg("转移成功"), a.list_Get(1);
                      }
                    );
                } else layer.close(t);
              },
            });
          } else layer.msg("请先选择一项进行操作");
        },
        del: function () {
          var a = this;
          if (this.checks.length)
            var t = layer.confirm("确认删除客户?", function () {
              yocto.json(config.api_del, { id: a.checks }, function (e) {
                layer.close(t), layer.msg("删除成功"), a.list_Get(1);
              });
            });
          else layer.msg("请先选择一项进行操作");
        },
        loading: function (e) {
          "close" == e
            ? layer.close(this.loadingSwitch)
            : (this.loadingSwitch = layer.load(3));
        },
        sale_Get: function (e) {
          var a = [];
          if ("object" != (void 0 === e ? "undefined" : _typeof(e))) a.push(e);
          else {
            if (!this.checks.length)
              return void layer.msg("请先选择一项进行操作");
            a = this.checks;
          }
          var t = this,
            i = "";
          0 == t.isKhGh
            ? (i = 0)
            : 3 == t.isKhGh
            ? (i = 1)
            : 1 == t.isKhGh
            ? (i = 3)
            : 4 == t.isKhGh
            ? (i = 4)
            : 5 == t.isKhGh
            ? (i = 5)
            : 6 == t.isKhGh
            ? (i = 6)
            : 7 == t.isKhGh && (i = 7),
            $.post(config.api_qdKhgh, { ids: a.join(","), type: i }).done(
              function (e) {
                e.result
                  ? layer.msg(e.result)
                  : layer.msg("操作成功,请在潜在客户查看"),
                  t.list_Get();
              }
            );
        },
        tab: function (e) {
          var a = this;
          this.isKhGh != e &&
            (this.clear(),
            (this.isKhGh = e),
            $("#searchForm")[0].reset(),
            this.s.lastSaleName.val(null).trigger("change"),
            (this.postData.saleName = ""),
            (this.postData.status = ""),
            (this.checks = []),
            (this.checkAll = 0),
            this.list_Get(1),
            this.uploader.option("formData", { isKhgh: this.isKhGh })),
            5 == e
              ? ((a.yyxcy = !0),
                (a.wyf = !1),
                (a.wyf1 = !0),
                (a.yycy1 = !1),
                (a.yycy = !0),
                (this.s.lastSaleName = this.select_init(
                  "[name=saleName]",
                  "请选择电销员工",
                  1,
                  "7d2f882f13ea48e0a4c8acaeea53b3a5,30b1487221464d369ca4c2462ccca920,01dc6d29f1704efeab0376d327f47d98"
                )))
              : 4 == e || 7 == e || 6 == e
              ? ((a.yyxcy = !1),
                (a.wyf = !0),
                (a.wyf1 = !1),
                (a.yycy1 = !0),
                (a.yycy = !1),
                (this.s.lastSaleName = this.select_init(
                  "[name=saleName]",
                  "请选择前负责人",
                  1,
                  "e350acd05a6244c79136616302b7dfd6,b729e9334ad64c15a4e47d88b8c2638f,008615f04f39499fb73e5f323add988a,6f247fed28e94194ac66020e55957496,28f1842e2463492a9eb6ddf0def91658,73340766cf3d43af91daff3128adad84,b62d6d19bed64ee199f2c1a5b252b57b"
                )))
              : 3 == e
              ? ((a.yyxcy = !1),
                (a.wyf = !0),
                (a.wyf1 = !1),
                (a.yycy1 = !0),
                (a.yycy = !1),
                (this.s.lastSaleName = this.select_init(
                  "[name=saleName]",
                  "请选择前负责人",
                  1,
                  "7d2f882f13ea48e0a4c8acaeea53b3a5,30b1487221464d369ca4c2462ccca920,01dc6d29f1704efeab0376d327f47d98,90564dd8b75a4f6d815ce418462d401c"
                )))
              : ((a.yyxcy = !1),
                (a.wyf = !0),
                (a.wyf1 = !1),
                (a.yycy1 = !0),
                (a.yycy = !1),
                (this.s.lastSaleName = this.select_init(
                  "[name=saleName]",
                  "请选择前负责人",
                  1,
                  "7d2f882f13ea48e0a4c8acaeea53b3a5,30b1487221464d369ca4c2462ccca920,01dc6d29f1704efeab0376d327f47d98,e350acd05a6244c79136616302b7dfd6,b729e9334ad64c15a4e47d88b8c2638f,008615f04f39499fb73e5f323add988a,6f247fed28e94194ac66020e55957496,28f1842e2463492a9eb6ddf0def91658,73340766cf3d43af91daff3128adad84,b62d6d19bed64ee199f2c1a5b252b57b"
                ))),
            (a.yyx = 3 != e),
            5 == e || 3 == e
              ? $("#excel_fileUp").hide()
              : $("#excel_fileUp").show();
        },
        search: function () {
          (this.postData.saleName = $.trim($("[name=saleName]").val())),
            (this.postData.status = $.trim($("[name=status]").val())),
            (this.postData.provinceName = $.trim(
              $("[name=provinceName]").val()
            )),
            (this.postData.cityName = $.trim($("[name=cityName]").val())),
            (this.postData.area = $.trim($("[name=area]").val())),
            (this.postData.keywords = $.trim($("[name=keywords]").val())),
            console.log(this.postData),
            this.list_Get(1);
        },
        list_Get: function (a) {
          $(".appWrapper").scrollTop(0), (this.checks = []);
          var t = this;
          a && (this.c.pageNum = a),
            t.loading(),
            (t.postData.isKhGh = this.isKhGh),
            (t.postData.pageSize = this.c.pageSize),
            (t.postData.pageNo = this.c.pageNum),
            require(["pagination"], function (e) {
              $.ajax({
                url: config.api_showSaleCustomer,
                async: !1,
                data: t.postData,
              }).done(function (e) {
                (t.c = e.result),
                  t.pagi
                    ? ($(".pagi").pagination("updatePages", t.c.pages),
                      1 == a && $(".pagi").pagination("goToPage", t.c.pageNum))
                    : ((t.pagi = 1),
                      $(".pagi").pagination({
                        pages: t.c.pages,
                        showCtrl: !0,
                        displayPage: 6,
                        currentPage: t.c.pageNum,
                        onSelect: function (e) {
                          (t.c.pageNum = e), t.list_Get();
                        },
                      })),
                  t.loading("close");
              });
            });
        },
        select_init: function (e, a, t, i) {
          return $(e).select2({
            placeholder: a || "请选择",
            language: "zh-CN",
            allowClear: t || !1,
            ajax: {
              url: "/user/getUserInfo",
              dataType: "json",
              type: "post",
              delay: 250,
              data: function (e) {
                return {
                  page: e.page || 1,
                  ROLE_ID: i || "",
                  data: { q: e.term || "" },
                };
              },
              processResults: function (e, a) {
                return (
                  (a.page = a.page || 1),
                  $.each(e, function () {
                    (this.id = this.USER_ID), (this.text = this.NAME);
                  }),
                  { results: e, pagination: { more: 10 == e.length } }
                );
              },
              cache: !0,
            },
            minimumInputLength: 0,
          });
        },
        sf: function () {
          $.get(config.api_isGh, function (e) {
            "00" == e.error &&
              ("有" == e.msg ? (this.isGh = 1) : (this.isGh = 2));
          });
        },
        customerView: function (e) {
          layer.open({
            type: 2,
            title: "查看详情",
            content: "/static/page/customer_detail.html?id=" + e.id,
            area: ["100%", "100%"],
          });
        },
      },
    });
  }),
  (window.parentFnc = function (e) {
    layer.closeAll(), window.app.getCaseRecord(window.app.type);
  });
