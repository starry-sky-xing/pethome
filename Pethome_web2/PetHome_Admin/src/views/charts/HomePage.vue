<template>
    <div class="errPage-container">

        <el-row>
            <el-col :span="12">
                <h5 class="text-jumbo text-ginormous">
                    欢迎光临~!
                </h5>
                <ul class="list-unstyled">
                    <li>你想干嘛呢:</li>
                    <li class="link-type">
                        <a href="http://news.baidu.com/">随便看看</a>
                    </li>
                    <li><a href="#" @click.prSevent="dialogVisible=true">点我看图</a></li>
                </ul>
            </el-col>
            <el-col :span="12">
                <img :src="errGif" width="600" height="428" alt="Girl has dropped her ice cream.">
            </el-col>
        </el-row>
        <el-dialog :visible.sync="dialogVisible" title="随便看">
            <img :src="ewizardClap" class="pan-img">
        </el-dialog>
    </div>
</template>

<script>
    import errGif from '../../assets/401_images/401.gif'

    export default {
        name: 'Page401',
        data() {
            return {
                errGif: errGif + '?' + +new Date(),
                ewizardClap: 'https://wpimg.wallstcn.com/007ef517-bafd-4066-aae4-6883632d9646',
                dialogVisible: false
            }
        },
        methods: {
            back() {
                if (this.$route.query.noGoBack) {
                    this.$router.push({
                        path: '/dashboard'
                    })
                } else {
                    this.$router.go(-1)
                }
            }
        }
    }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
    .errPage-container {
        width: 800px;
        max-width: 100%;
        margin: 100px auto;

        .pan-back-btn {
            background: #008489;
            color: #fff;
            border: none !important;
        }

        .pan-gif {
            margin: 0 auto;
            display: block;
        }

        .pan-img {
            display: block;
            margin: 0 auto;
            width: 100%;
        }

        .text-jumbo {
            font-size: 60px;
            font-weight: 700;
            color: #484848;
        }

        .list-unstyled {
            font-size: 14px;

            li {
                padding-bottom: 5px;
            }

            a {
                color: #008489;
                text-decoration: none;

                &:hover {
                    text-decoration: underline;
                }
            }
        }
    }
</style>
